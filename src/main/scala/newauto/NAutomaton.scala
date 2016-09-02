package newauto

/**
  * Created by Basil on 31/08/2016.
  */
trait Automaton {
  val state: List[NCell]
  def next: List[NCell]
}

abstract class AutomatonTemplate(val state: List[NCell]) extends Automaton {

  def constructGrid: List[NCell] => Grid
  def defineNeighbours: Grid => Neighbourhood
  def traverseAutomaton: (Grid, Neighbourhood) => List[NCell]

  // state.map(c => NewCell(c.transitionFunction(c.currentState, ns), c.transitionFunction))

  override def next: List[NCell] = {
    val g = constructGrid(state)
    val ns = defineNeighbours(g)

    traverseAutomaton(g, ns)
  }

}

case class OneDim(override val state: List[NCell])(x: Int) extends AutomatonTemplate(state) {

  override def constructGrid: List[NCell] => Grid = GridBuilder.oneDimensionalGrid(x)

  override def defineNeighbours: Grid => Neighbourhood = NeighbourhoodBuilder.oneDimensionalRadiusOne(x)

  override def traverseAutomaton: (Grid, Neighbourhood) => List[NCell] = TraversalBuilder.oneDimensionalTraversal(x)

}

case class ThreeDim(override val state: List[NCell])(x: Int, y: Int, z: Int) extends AutomatonTemplate(state) {

  override def constructGrid: List[NCell] => Grid = GridBuilder.threeDimenionalgrid(x, y, z)

  override def defineNeighbours: Grid => Neighbourhood = NeighbourhoodBuilder.threeDimensionalRadiusOne(x, y, z)

  override def traverseAutomaton: (Grid, Neighbourhood) => List[NCell] = TraversalBuilder.threeDimensionalTraversal(x, y, z)

}

case class ThreeDimPar(override val state: List[NCell])(x: Int, y: Int, z: Int)(t: Int) extends AutomatonTemplate(state) {

  override def constructGrid: List[NCell] => Grid = GridBuilder.threeDimenionalgrid(x, y, z)

  override def defineNeighbours: Grid => Neighbourhood = NeighbourhoodBuilder.threeDimensionalRadiusOne(x, y, z)

  override def traverseAutomaton: (Grid, Neighbourhood) => List[NCell] = TraversalBuilder.threeDimensionalTraversalPar(x, y, z)(t)

}

case class GardenPar(override val state: List[NCell])(x: Int, y: Int, z: Int)(t: Int) extends AutomatonTemplate(state) {

  override def constructGrid: List[NCell] => Grid = GridBuilder.threeDimenionalgrid(x, y, z)

  override def defineNeighbours: Grid => Neighbourhood = NeighbourhoodBuilder.threeDimensionalRadiusOne(x, y, z)

  override def traverseAutomaton: (Grid, Neighbourhood) => List[NCell] = TraversalBuilder.threeDimensionalTraversal(x, y, z)

}