package automaton.automata

import automaton._
import automaton.garden._
import parallel.TaskManager



/**
  * Created by Basil on 17/08/2016.
  */
case class Garden(init: List[State], x: Int, y: Int, z: Int, parallel: Boolean, threshold: Int) extends Automata with AllNeighbours {

  require(init.length == x * y * z, "Number of states provided must match dimensions")

  val inputWithIndex = init zipWithIndex
  val grid = coordinateTable(inputWithIndex, x, y, z)

  /* M E T H O D S */

  def coordinateTable(init: Configuration, xDim: Int, yDim: Int, zDim: Int): Grid = {
    init.map(in => (indexToCoordinate(xDim, yDim, zDim)(in._2), in)).toMap
  }

  def indexToCoordinate(x: Int, y: Int, z: Int)(i: Int): Coordinate = {
    (
      if (x==0) 0
      else i % x,
      if (y==0) 0
      else (if (x==0) 0
      else (i / x) % y),
      if (z==0) 0
      else if (x==0 || y==0) 0
      else (i / (x * y)) % z
      )
  }

  def getCoordinate = indexToCoordinate(x, y, z)(_)

  def traverse(in: Configuration): Global = {

    in.map(c => {
      val state = c._1
      val index = c._2
      val coord = getCoordinate(index)
      val n = neighbours(coord)
      val ns = neighbourStates(n)
      rule(state, ns)
    })

  }

  def reduce(in: Configuration, t: Int): Global = {
    if (in.length < t) {
      traverse(in)
    } else {

      val m = in.length / 2
      val l = in.take(m)
      val r = in.drop(m)

      val (t1, t2) = TaskManager.parallel(reduce(l, t), reduce(r, t))

      t1 ::: t2

    }
  }

  def boundary(i: Int): Int = i
  def neighbours(in: Coordinate): Map[String, Coordinate] = {

    Map(
      "RIGHT" -> (boundary(in._1 - 1), in._2, in._3)
      , "LEFT" -> (boundary(in._1 + 1), in._2, in._3)
      , "TOP" -> (in._1, in._2, boundary(in._3 + 1))
      , "BOTTOM" -> (in._1, in._2, boundary(in._3 - 1))
      , "FRONT" -> (in._1, boundary(in._2 - 1), in._3)
      , "BACK" -> (in._1, boundary(in._2 + 1), in._3)

    )

  }

  def neighbourStates(in: Map[String, Coordinate]): Neighbours = mapToMap[String, Coordinate, State](in, c => grid.getOrElse(c, (PadState("P", Vect(0.0,0.0,0.0)), -1))._1)

  def mapToMap[A,B,C](in: Map[A, B], op: B => C): Map[A, C] = {
    def h(acc: Map[A, C], ks: Set[A]): Map[A, C] = ks.toList match {
      case Nil => acc
      case x :: xs => {
        val v = in(x)
        val e = op(v)
        h(acc + (x -> e), ks - x)
      }
    }
    h(Map.empty[A, C], in.keySet)
  }

  override def next: List[State] = {
    if (parallel) reduce(inputWithIndex, threshold)
    else traverse(inputWithIndex)
  }

}
