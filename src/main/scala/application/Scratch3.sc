// package object...

trait State
type Configuration = List[State]
type Coordinate = List[Int]
type Dimensions = List[Int]
type Cell = (Coordinate, State)
type NeighbourCoordinates = Map[String, Coordinate]
type NeighbourCells = Map[String, Cell]
type NeighbourStates = Map[String, State]

trait Neighbourhood {
  def neighbours(c: Coordinate, dims: Dimensions): NeighbourCoordinates
}
trait BasicRadiusOne extends Neighbourhood {
  override def neighbours(c: Coordinate, dims: Dimensions): NeighbourCoordinates = {

    require(c.length == 1, "Not a 1-dimensional Coordinate")
    require(c.head >= 0, "No negative coordinates")
    require(dims.length == c.length, "Incorrect dimensions provided")
    require(dims.head > 0, "Zero dimension not permitted")

    val index = c.head
    val width = dims.head
    val radius = 1

    if (index == 0) Map("RIGHT" -> List(1))
    else if (index == width) Map("LEFT" -> List(index - radius))
    else Map("LEFT" -> List(index - radius), "RIGHT" -> List(index + radius))

  }
}

trait ToroidalRadiusOne extends Neighbourhood {
  override def neighbours(c: Coordinate, dims: Dimensions): NeighbourCoordinates = ???
}
trait Moore extends Neighbourhood {
  override def neighbours(c: Coordinate, dims: Dimensions): NeighbourCoordinates = ???
}
trait Neumann extends Neighbourhood {
  override def neighbours(c: Coordinate, dims: Dimensions): NeighbourCoordinates = ???
}

trait Transition {
  def rule(current: State, neighbouring: NeighbourStates): State
}
trait Rule90 extends Transition {
  override def rule(current: State, neighbouring: NeighbourStates): State = {

  }
}

trait Automaton {
  def next: Configuration
}

class Line(width: Int, init: Configuration) extends Automaton with Moore with Rule90 {

  require(width > 0, "Line width must be greater than 0")
  require(init.length == width, "Number of given states must match the total width of the line.")

  val initWithIndex = init zipWithIndex

  override def next: Configuration = ???

}

