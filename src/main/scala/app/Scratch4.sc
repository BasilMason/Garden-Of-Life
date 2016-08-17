import parallel.TaskManager

// builder/algo/strategy pattern?

type State = Int
type Global = List[State]
type Coordinate = (Int, Int, Int)
type Neighbours = Map[String, State]
type Configuration = List[Cell]
type Grid = Map[Coordinate, Cell]
type Cell = (State, Int)

val x = 3
val y = 3
val z = 3
val input = List(0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0)
val inputWithIndex = input zipWithIndex
val grid = coordinateTable(inputWithIndex, x, y, z)

def traverse(in: Configuration): Global = {

  in.map(c => {
    val state = c._1
    val index = c._2
    val coord = getCoordinate(index)
    val n = neighbours(coord)
    val ns = neighbourStates(n)
    transition(state, ns)
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

val l = (0,1,1)


def boundary(i: Int): Int = i
def neighbours(in: Coordinate): Map[String, Coordinate] = {

  Map(
    "LEFT" -> (boundary(in._1 - 1), in._2, in._3)
    , "RIGHT" -> (boundary(in._1 + 1), in._2, in._3)
    , "TOP" -> (in._1, boundary(in._2 - 1), in._3)
    , "BOTTOM" -> (in._1, boundary(in._2 + 1), in._3)
    , "FRONT" -> (in._1, in._2, boundary(in._3 - 1))
    , "BACK" -> (in._1, in._2, boundary(in._3 - 1))

    , "TOP_LEFT" -> (boundary(in._1 - 1), in._2, in._3)
    , "TOP_RIGHT" -> (boundary(in._1 + 1), in._2, in._3)
    , "BOTTOM_LEFT" -> (in._1, boundary(in._2 - 1), in._3)
    , "BOTTOM_RIGHT" -> (in._1, boundary(in._2 + 1), in._3)
    , "FRONT_TOP_LEFT" -> (in._1, in._2, boundary(in._3 - 1))
    , "FRONT_TOP" -> (in._1, in._2, boundary(in._3 - 1))
  )
}

def neighbourStates(in: Map[String, Coordinate]): Neighbours = mapToMap[String, Coordinate, State](in, c => grid.getOrElse(c, (-1, -1))._1)

val n = neighbours(l)

trait Neighbourhood {
  def neighbours(location: Coordinate): Neighbours
}

trait TwoDimensionalMoore extends Neighbourhood {
  override def neighbours(location: Coordinate): Neighbours = ???
}

trait ThreeDimenionalMooreBasic extends Neighbourhood {
  override def neighbours(location: Coordinate): Neighbours = ???
}

def coordinateTable(init: Configuration, xDim: Int, yDim: Int, zDim: Int): Grid = {

  require(init.length == xDim * yDim * zDim, "Number of states must match degrees of dimensions")

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

def transition(current: State, ns: Neighbours): State = {

  1

}

def mapToMap[A,B,C](in: Map[A, B], op: B => C): Map[A, C] = {
  def h(acc: Map[A, C], ks: Set[A]): Map[A, C] = ks.toList match {
    case Nil => acc
    case x :: xs => {
      val v = in(x)
      val e = op(v)
      h(acc + (x -> e), xs.toSet)
    }
  }
  h(Map.empty[A, C], in.keySet)
}

