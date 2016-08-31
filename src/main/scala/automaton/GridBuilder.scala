package newauto

case object GridBuilder {

  type Grid = Map[VectorN, NCell]

  def oneDimensionalGrid(x: Int): List[NCell] => Grid = (init: List[NCell]) => {

    require(init.length == x, "Number of states provided must match dimensions")

    val initWithIndex = init.zipWithIndex
    initWithIndex.map(p => (Vector1(p._2), p._1)).toMap
  }

  def twoDimenionalgrid(x: Int, y: Int): List[NCell] => Grid = (init: List[NCell]) => {

    require(init.length == x * y, "Number of states provided must match dimensions")

    def indexToCoordinate(x: Int, y: Int)(i: Int): Vector2 = {
      Vector2(
        if (x==0) 0
        else i % x,
        if (y==0) 0
        else if (x==0) 0
        else (i / x) % y
      )
    }

    val initWithIndex = init.zipWithIndex
    initWithIndex.map(in => (indexToCoordinate(x, y)(in._2), in._1)).toMap

  }

  def threeDimenionalgrid(x: Int, y: Int, z: Int): List[NCell] => Grid = (init: List[NCell]) => {

    require(init.length == x * y * z, "Number of states provided must match dimensions")

    def indexToCoordinate(x: Int, y: Int, z: Int)(i: Int): Vector3 = {
      Vector3(
        if (x==0) 0
        else i % x,
        if (y==0) 0
        else if (x==0) 0
        else (i / x) % y,
        if (z==0) 0
        else if (x==0 || y==0) 0
        else (i / (x * y)) % z
      )
    }

    val initWithIndex = init.zipWithIndex
    initWithIndex.map(in => (indexToCoordinate(x, y, z)(in._2), in._1)).toMap
  }

}
