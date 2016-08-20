import automaton.garden.State

/**
  * Created by Basil on 20/08/2016.
  */
package object automaton {

  type Global = List[State]
  type Coordinate = (Int, Int, Int)
  type Neighbours = Map[String, State]
  type Configuration = List[Cell]
  type Grid = Map[Coordinate, Cell]
  type Cell = (State, Int)

  case class Vect(x: Double, y: Double, z: Double)

}
