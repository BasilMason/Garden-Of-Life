sealed trait VectorN
case class Vector1(x: Int) extends VectorN
case class Vector2(x: Int, y: Int) extends VectorN
case class Vector3(x: Int, y: Int, z: Int) extends VectorN

def oneDimensionalGrid(x: Int): List[String] => Map[Vector1, String] = (init: List[String]) => {

  require(init.length == x, "Number of states provided must match dimensions")

  val initWithIndex = init.zipWithIndex
  initWithIndex.map(p => (Vector1(p._2), p._1)).toMap

}

def twoDimenionalgrid(x: Int, y: Int): List[String] => Map[Vector2, String] = (init: List[String]) => {

  require(init.length == x * y, "Number of states provided must match dimensions")

  def indexToCoordinate(x: Int, y: Int)(i: Int): Vector2 = {
    Vector2(
      if (x==0) 0
      else i % x,
      if (y==0) 0
      else (if (x==0) 0
      else (i / x) % y)
    )
  }

  val initWithIndex = init.zipWithIndex
  initWithIndex.map(in => (indexToCoordinate(x, y)(in._2), in._1)).toMap

}

def threeDimenionalgrid(x: Int, y: Int, z: Int): List[String] => Map[Vector3, String] = (init: List[String]) => {

  require(init.length == x * y * z, "Number of states provided must match dimensions")

  def indexToCoordinate(x: Int, y: Int, z: Int)(i: Int): Vector3 = {
    Vector3(
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

  val initWithIndex = init.zipWithIndex
  initWithIndex.map(in => (indexToCoordinate(x, y, z)(in._2), in._1)).toMap
}

val l1 = List("a", "b", "c")
val l2 = List("a", "b", "c", "d", "e", "f", "g", "h", "i")
val l3 = List("a", "b", "c", "d", "e", "f", "g", "h")
val g1 = oneDimensionalGrid(3)(l1)
val g2 = twoDimenionalgrid(3,3)(l2)
val g3 = threeDimenionalgrid(2,2,2)(l3)

val v1 = Vector1(3)
val n = v1.x

