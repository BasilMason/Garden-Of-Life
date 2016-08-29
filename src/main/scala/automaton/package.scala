import automaton.garden.{BinaryState, GardenState, State}

/**
  * Created by Basil on 20/08/2016.
  */
package object automaton {

  type Global = List[State]
  type GGlobal = List[GardenState]

  type Coordinate = (Int, Int, Int)

  type Neighbours = Map[String, State]
  type BNeighbours = Map[String, BinaryState]
  type GNeighbours = Map[String, GardenState] // make neighbourhood

  type Configuration = List[Cell]
  type GConfiguration = List[GCell]

  type Grid = Map[Coordinate, Cell]
  type GGrid = Map[Coordinate, GCell]

  type Cell = (State, Int)
  type GCell = (GardenState, Int)

  case class Vect(x: Double, y: Double, z: Double)

}
