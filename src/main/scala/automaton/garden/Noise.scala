package automaton.garden

import scala.util.Random

/**
  * Created by Basil on 19/08/2016.
  */
object Noise {

  def getNoise(x: Int, y: Int, amp: Int): List[((Int, Int), Int)] = {

    type Coordinate = (Int, Int)
    type Neighbours = Map[String, Int]
    type Configuration = List[(Int, Int)]
    type Grid = Map[Coordinate, (Int, Int)]

    def coordinateTable(init: Configuration, xDim: Int, yDim: Int): Grid = {
      init.map(in => (indexToCoordinate(xDim, yDim)(in._2), in)).toMap
    }

    def indexToCoordinate(x: Int, y: Int)(i: Int): Coordinate = {
      (
        if (x==0) 0
        else i % x,
        if (y==0) 0
        else (if (x==0) 0
        else (i / x) % y)
        )
    }
    def getCoordinate = indexToCoordinate(x, y)(_)

    def boundary(i: Int): Int = i
    def neighbours(in: Coordinate): Map[String, Coordinate] = {

      Map(
        "LEFT" -> (boundary(in._1 - 1), in._2)
        , "RIGHT" -> (boundary(in._1 + 1), in._2)
        , "TOP" -> (in._1, boundary(in._2 - 1))
        , "BOTTOM" -> (in._1, boundary(in._2 + 1))
      )

    }



    def transition(current: Int, ns: Neighbours): Int = {

      val ss: List[Int] = ns.values.toList.filter(x => x >= 0)
      val av: Int = ((ss.sum / ss.length) * 75) / 100

      av

    }

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

    val r = Random
    val init = for {
      ys <- (0 until y)
      xs <- (0 until x)
    } yield (Math.abs(r.nextInt()) % amp)

    val inputWithIndex = init.toList zipWithIndex
    val grid = coordinateTable(inputWithIndex, x, y)
    def neighbourStates(in: Map[String, Coordinate]): Neighbours = mapToMap[String, Coordinate, Int](in, c => grid.getOrElse(c, (-1, -1))._1)
    def traverse(in: Configuration): List[Int] = {

      in.map(c => {
        val state = c._1
        val index = c._2
        val coord = getCoordinate(index)
        val n = neighbours(coord)
        val ns = neighbourStates(n)
        transition(state, ns)
      })

    }
    val noise = traverse(inputWithIndex)

    noise.zipWithIndex.map(p => (getCoordinate(p._2), p._1))

  }

}
