package automaton.garden

import automaton.Vect

/**
  * Created by Basil on 13/07/2016.
  */
sealed trait State
abstract class BinaryState extends State

case object RedState extends BinaryState
case object BlueState extends BinaryState
case object GreenState extends BinaryState
case object PadState extends BinaryState
case object NilState extends BinaryState
case object Alive extends BinaryState
case object Dead extends BinaryState

abstract class GardenState(val wind: Double, val sun: Double, val water: Double, val gravity: Double, val velocity: Vect, val age: Int, val volume: Int) extends State {

  def this() = {
    this(0.0, 0.0, 0.0, 0.0, Vect(0.0, 0.0, 0.0), 0, 0)
  }

}

case class SkyState(override val wind: Double, override val sun: Double, override val water: Double, override val gravity: Double, override val velocity: Vect, override val age: Int, override val volume: Int) extends GardenState(wind, sun, water, gravity, velocity, age, volume)
case class GrassState(override val wind: Double, override val sun: Double, override val water: Double, override val gravity: Double, override val velocity: Vect, override val age: Int, override val volume: Int) extends GardenState(wind, sun, water, gravity, velocity, age, volume)
case class EarthState(override val wind: Double, override val sun: Double, override val water: Double, override val gravity: Double, override val velocity: Vect, override val age: Int, override val volume: Int) extends GardenState(wind, sun, water, gravity, velocity, age, volume)
case class PlantState(override val wind: Double, override val sun: Double, override val water: Double, override val gravity: Double, override val velocity: Vect, override val age: Int, override val volume: Int) extends GardenState(wind, sun, water, gravity, velocity, age, volume)
case class FlowerState(override val wind: Double, override val sun: Double, override val water: Double, override val gravity: Double, override val velocity: Vect, override val age: Int, override val volume: Int) extends GardenState(wind, sun, water, gravity, velocity, age, volume)
case class SunState(override val wind: Double, override val sun: Double, override val water: Double, override val gravity: Double, override val velocity: Vect, override val age: Int, override val volume: Int) extends GardenState(wind, sun, water, gravity, velocity, age, volume)
case class TreeState(override val wind: Double, override val sun: Double, override val water: Double, override val gravity: Double, override val velocity: Vect, override val age: Int, override val volume: Int) extends GardenState(wind, sun, water, gravity, velocity, age, volume)
case class CloudState(override val wind: Double, override val sun: Double, override val water: Double, override val gravity: Double, override val velocity: Vect, override val age: Int, override val volume: Int) extends GardenState(wind, sun, water, gravity, velocity, age, volume)
case class VoidState(override val wind: Double, override val sun: Double, override val water: Double, override val gravity: Double, override val velocity: Vect, override val age: Int, override val volume: Int) extends GardenState(wind, sun, water, gravity, velocity, age, volume)

