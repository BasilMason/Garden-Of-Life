package newauto

/**
  * Created by Basil on 31/08/2016.
  */
object TraversalBuilder {

  def oneDimensionalTraversal(x: Int): (Grid, Neighbourhood) => List[NCell] = (g, ns) => {
    //in.map(c => NewCell(c.transitionFunction(c.currentState, ns), c.transitionFunction))

    val l = for {
      xs <- (0 until x)
      c = g(Vector1(xs))
      n = ns(Vector1(xs))
    } yield NewCell(c.transitionFunction(c.currentState, n), c.transitionFunction)

    l.toList

  }

}
