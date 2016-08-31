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

}
