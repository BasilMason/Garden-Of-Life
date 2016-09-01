package newauto

/**
  * Created by Basil on 31/08/2016.
  */
case object NeighbourhoodBuilder {

  def oneDimensionalRadiusOne(x: Int): Grid => Neighbourhood = (g: Grid) => {
    g.map(p => (p._1, p._1 match {
      case Vector1(n) if n == 0 => Map("LEFT" -> NewCell(NDead,(s,n) => s), "RIGHT" -> g(Vector1(n + 1)))
      case Vector1(n) if n == (x - 1) => Map("LEFT" -> g(Vector1(n - 1)), "RIGHT" -> NewCell(NDead,(s,n) => s))
      case Vector1(n) => Map("LEFT" -> g(Vector1(n - 1)), "RIGHT" -> g(Vector1(n + 1)))
    }))
  }

  def threeDimensionalRadiusOne(x: Int, y: Int, z: Int): Grid => Neighbourhood = (g: Grid) => {

    g.map(p => (p._1, p._1 match {
      case Vector3(i, j, k) => Map(
        "RIGHT" -> g.getOrElse(Vector3(i - 1, j, k), NewCell(NDead,(s,n) => s))
        , "LEFT" -> g.getOrElse(Vector3(i + 1, j, k), NewCell(NDead,(s,n) => s))
        , "TOP" -> g.getOrElse(Vector3(i, j - 1, k), NewCell(NDead,(s,n) => s))
        , "BOTTOM" -> g.getOrElse(Vector3(i, j + 1, k), NewCell(NDead,(s,n) => s))
        , "FRONT" -> g.getOrElse(Vector3(i, j, k - 1), NewCell(NDead,(s,n) => s))
        , "BACK" -> g.getOrElse(Vector3(i, j, k + 1), NewCell(NDead,(s,n) => s))
      )
    }))

  }

}
