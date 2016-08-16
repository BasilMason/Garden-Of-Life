type Cell = (Int, Int)              // state, index
type Coordinate = (Int, Int, Int)   // x, y, z
type Transition = Cell => Int

val x = 3
val y = 3
val z = 3
def indexToCoordinate(x: Int, y: Int, z: Int)(i: Int): Coordinate = (if (x==0) 0 else i % x, if (y==0) 0 else (if (x==0) 0 else (i / x) % y), if (z==0) 0 else if (x==0 || y==0) 0 else (i / (x * y)) % z)
def getCoordinate = indexToCoordinate(x, y, z)(_)
val init = List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
val initWithIndex: List[Cell] = init zipWithIndex
val coordinateTable: Map[Coordinate, Cell] = initWithIndex.map(in => (getCoordinate(in._2), in)).toMap
def listToCoordinate(l: List[Int]): Coordinate = l match {
  case x :: y :: z :: Nil => (x, y, z)
}
def coordinateToList(c: Coordinate): List[Int] = List(c._1, c._2, c._3)


def clamp(i: Int): Int = if (i < 0) 0 else i
def neighbours(in: Coordinate): Map[String, List[Int]] = {
  def h(acc: List[List[Int]], inp: List[Int], idx: Int): List[List[Int]] = idx match {
    case 3 => acc
    case n => h(acc ::: List(inp.updated(n, clamp(inp(n) - 1)), inp.updated(n, clamp(inp(n) + 1))), inp, n+1)
  }
  (List("LEFT", "RIGHT", "BACK", "FRONT", "BOTTOM", "TOP") zip h(List(), coordinateToList(in), 0)).toMap
}

val c = (1,1,1)
val ns = neighbours(c).values.toSet.filter(i => i != coordinateToList(c))
val cs = ns.toList.map(n => coordinateTable(listToCoordinate(n))._1).sum

def step(c: Cell): Int = {
  val cs = getCoordinate(c._1)
  val ns = neighbours(cs).values.toSet.filter(i => i != coordinateToList(cs))
  ns.toList.map(n => coordinateTable(listToCoordinate(n))._1).sum
}










