package automaton.garden

import automaton.Vect

/**
  * Created by Basil on 13/07/2016.
  */
sealed trait State {
  val s: String
  val velocity: Vect
}
case class RedState(s: String, velocity: Vect) extends State
case class BlueState(s: String, velocity: Vect) extends State
case class GreenState(s: String, velocity: Vect) extends State
case class PadState(s: String, velocity: Vect) extends State
case class NilState(s: String, velocity: Vect) extends State

abstract class GardenState extends State {
  val s: String
  val wind: Double
  val sun: Double
  val water: Double
  val gravity: Double
  val velocity: Vect
  val age: Int
  val volume: Int
}
case class SkyState(s: String, wind: Double, sun: Double, water: Double, gravity: Double, velocity: Vect, age: Int, volume: Int) extends GardenState
case class GrassState(s: String, wind: Double, sun: Double, water: Double, gravity: Double, velocity: Vect, age: Int, volume: Int) extends GardenState
case class EarthState(s: String, wind: Double, sun: Double, water: Double, gravity: Double, velocity: Vect, age: Int, volume: Int) extends GardenState
case class PlantState(s: String, wind: Double, sun: Double, water: Double, gravity: Double, velocity: Vect, age: Int, volume: Int) extends GardenState
case class FlowerState(s: String, wind: Double, sun: Double, water: Double, gravity: Double, velocity: Vect, age: Int, volume: Int) extends GardenState
case class SunState(s: String, wind: Double, sun: Double, water: Double, gravity: Double, velocity: Vect, age: Int, volume: Int) extends GardenState
case class TreeState(s: String, wind: Double, sun: Double, water: Double, gravity: Double, velocity: Vect, age: Int, volume: Int) extends GardenState
case class CloudState(s: String, wind: Double, sun: Double, water: Double, gravity: Double, velocity: Vect, age: Int, volume: Int) extends GardenState

