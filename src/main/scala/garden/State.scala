package garden

/**
  * Created by Basil on 13/07/2016.
  */
sealed trait State {
  val s: String
}
case class RedState(s: String) extends State
case class BlueState(s: String) extends State
case class GreenState(s: String) extends State
case class PadState(s: String) extends State
case class NilState(s: String) extends State

abstract class GardenState extends State {
  val s: String
  val wind: Double
  val sun: Double
  val water: Double
  val gravity: Double
  val velocity: (Double, Double, Double)
}
case class SkyState(s: String, wind: Double, sun: Double, water: Double, gravity: Double, velocity: (Double, Double, Double)) extends GardenState
case class GrassState(s: String, wind: Double, sun: Double, water: Double, gravity: Double, velocity: (Double, Double, Double)) extends GardenState
case class EarthState(s: String, wind: Double, sun: Double, water: Double, gravity: Double, velocity: (Double, Double, Double)) extends GardenState
case class PlantState(s: String, wind: Double, sun: Double, water: Double, gravity: Double, velocity: (Double, Double, Double)) extends GardenState

