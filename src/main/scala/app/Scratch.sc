/**
  * 1. Receive list of initial states
  * 2. Assign indexes to initial states
  * 3. Build coordinate table
  * 4.
  */
object Site {
  final val LEFT = 0
  final val RIGHT = 1
  final val BACK = 2
  final val FRONT = 3
  final val TOP = 4
  final val BOTTOM = 5
}
type Cell = (String, Int)
type Coordinate = (Int, Int, Int)
type Transition = Cell => String

val x = 3
val y = 3
val z = 3
def indexToCoordinate(x: Int, y: Int, z: Int)(i: Int): Coordinate = (if (x==0) 0 else i % x, if (y==0) 0 else (if (x==0) 0 else (i / x) % y), if (z==0) 0 else if (x==0 || y==0) 0 else (i / (x * y)) % z)
def getCoordinate = indexToCoordinate(x, y, z)(_)
val init = List("OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF", "OFF")
val initWithIndex = init zipWithIndex
val coordinateTable: Map[Coordinate, Cell] = initWithIndex.map(in => (getCoordinate(in._2), in)).toMap

// in % x
// (in / x) % y
// (in / (x * y)) % z
// if (x==0) 0 else in._2 % x
// if (y==0) 0 else (if (x==0) 0 else (in._2 / x) % y)
// if (z==0) 0 else if (x==0 || y==0) 0 else (in._2 / (x * y)) % z

//if (z==0) 0 else if (x==0 || y==0) 0 else (in._2 / (x * y)) % z

def clamp(i: Int): Int = if (i < 0) 0 else i
def neighbours(in: List[Int]): Map[String, List[Int]] = {
  def h(acc: List[List[Int]], inp: List[Int], idx: Int): List[List[Int]] = idx match {
    case 3 => acc
    case n => h(acc ::: List(inp.updated(n, clamp(inp(n) - 1)), inp.updated(n, clamp(inp(n) + 1))), inp, n+1)
  }
  (List("LEFT", "RIGHT", "BACK", "FRONT", "BOTTOM", "TOP") zip h(List(), in, 0)).toMap
}

def neighbours2(in: Coordinate): Map[String, List[Int]] = {
  def h(acc: List[List[Int]], inp: List[Int], idx: Int): List[List[Int]] = idx match {
    case 3 => acc
    case n => h(acc ::: List(inp.updated(n, clamp(inp(n) - 1)), inp.updated(n, clamp(inp(n) + 1))), inp, n+1)
  }
  (List("LEFT", "RIGHT", "BACK", "FRONT", "BOTTOM", "TOP") zip h(List(), List(in._1, in._2, in._3), 0)).toMap
}

def step(c: Cell): String = {
  val ns = neighbours2(getCoordinate(c._2))
}

for {
  i <- (0 until z)
  j <- (0 until y)
  k <- (0 until x)
} yield (i,j,k)



