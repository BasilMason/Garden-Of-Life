package newauto

/**
  * Created by Basil on 31/08/2016.
  */
sealed trait NState
sealed trait BinaryNState extends NState

case object NDead extends BinaryNState
case object NAlive extends BinaryNState

sealed trait GardenNState extends NState {
  val gravity = 10
  def water: Double
  def sunlight: Double
  def wind: Double
}
sealed trait StaticNState extends GardenNState
object StaticNState {
  def unapply(ss: StaticNState): Option[(Double, Double, Double)] =
    Option(ss) map { e =>
      (ss.water, ss.sunlight, ss.wind)
    }
}

sealed trait DynamicNState extends GardenNState {
  def velocity: List[Vector3]
  def age: Int
  def volume: Int
}
object DynamicNState {
  def unapply(ds: DynamicNState): Option[(Double, Double, Double, List[Vector3], Int, Int)] =
    Option(ds) map { e =>
      (ds.water, ds.sunlight, ds.wind, ds.velocity, ds.age, ds.volume)
    }
}

abstract class NStatic(_water: Double, _sunlight: Double, _wind: Double) extends StaticNState {
  override def water = _water
  override def sunlight = _sunlight
  override def wind = _wind
}

abstract class NDynamic(_water: Double, _sunlight: Double, _wind: Double, _velocity: List[Vector3], _age: Int, _volume: Int) extends DynamicNState {
  override def water = _water
  override def sunlight = _sunlight
  override def wind = _wind
  override def velocity = _velocity
  override def age = _age
  override def volume = _volume
}

case class NSoil(_water: Double, _sunlight: Double, _wind: Double) extends NStatic(_water, _sunlight, _wind)
case class NSky(_water: Double, _sunlight: Double, _wind: Double) extends NStatic(_water, _sunlight, _wind)

case class NGrass(_water: Double, _sunlight: Double, _wind: Double, _velocity: List[Vector3], _age: Int, _volume: Int) extends NDynamic(_water, _sunlight, _wind, _velocity, _age, _volume)
case class NPlant(_water: Double, _sunlight: Double, _wind: Double, _velocity: List[Vector3], _age: Int, _volume: Int) extends NDynamic(_water, _sunlight, _wind, _velocity, _age, _volume)
case class NTree(_water: Double, _sunlight: Double, _wind: Double, _velocity: List[Vector3], _age: Int, _volume: Int) extends NDynamic(_water, _sunlight, _wind, _velocity, _age, _volume)




// soil - for every plant type state nearby, reduce water by one
// sky - for every non-sky state nearby, reduce sunlight by one
