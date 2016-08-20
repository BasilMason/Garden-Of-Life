package automaton.garden

/**
  * Created by Basil on 13/07/2016.
  */
trait CellX {
  val state: State
  val index: Int
  val neighbours: Map[String, CellX]
}
case class RedCellX(val index: Int = Generator.get()) extends CellX {
  override val state: State = RedState("R")
  override val neighbours = Map[String, CellX]().empty
}
case class RedCellXWithNeighbour(val index: Int, val neighbours: Map[String, CellX]) extends CellX with Neighbourhood {
  override val state: State = RedState("R")
}
case class GreenCellX(val index: Int = Generator.get()) extends CellX {
  override val state: State = RedState("G")
  override val neighbours = Map[String, CellX]().empty
}
case class GreenCellXWithNeighbour(val index: Int, val neighbours: Map[String, CellX]) extends CellX with Neighbourhood {
  override val state: State = GreenState("G")
}
case class BlueCellX(val index: Int = Generator.get()) extends CellX {
  override val state: State = BlueState("B")
  override val neighbours = Map[String, CellX]().empty
}
case class BlueCellXWithNeighbour(val index: Int, val neighbours: Map[String, CellX]) extends CellX with Neighbourhood {
  override val state: State = BlueState("B")
}
case class PadCellX(val index: Int = Generator.get()) extends CellX {
  override val state: State = PadState("P")
  override val neighbours = Map[String, CellX]().empty
}
case class PadCellXWithNeighbour(val index: Int, val neighbours: Map[String, CellX]) extends CellX with Neighbourhood {
  override val state: State = PadState("P")
}
case class NilCellX(val index: Int = Generator.get()) extends CellX {
  override val state: State = NilState("N")
  override val neighbours = Map[String, CellX]().empty
}
case class NilCellXWithNeighbour(val index: Int, val neighbours: Map[String, CellX]) extends CellX with Neighbourhood {
  override val state: State = NilState("N")
}
case class SkyCellX(val index: Int = Generator.get(), val neighbours: Map[String, CellX] = Map[String, CellX]().empty, wind: Double, sun: Double, water: Double, gravity: Double, velocity: (Double, Double, Double)) extends CellX {
  override val state: State = SkyState("SS", wind, sun, water, gravity, velocity)
}
case class GrassCellX(val index: Int = Generator.get(), val neighbours: Map[String, CellX] = Map[String, CellX]().empty, wind: Double, sun: Double, water: Double, gravity: Double, velocity: (Double, Double, Double)) extends CellX {
  override val state: State = GrassState("GS", wind, sun, water, gravity, velocity)
}
case class EarthCellX(val index: Int = Generator.get(), val neighbours: Map[String, CellX] = Map[String, CellX]().empty, wind: Double, sun: Double, water: Double, gravity: Double, velocity: (Double, Double, Double)) extends CellX {
  override val state: State = EarthState("ES", wind, sun, water, gravity, velocity)
}
case class PlantCellX(val index: Int = Generator.get(), val neighbours: Map[String, CellX] = Map[String, CellX]().empty, wind: Double, sun: Double, water: Double, gravity: Double, velocity: (Double, Double, Double)) extends CellX {
  override val state: State = PlantState("PS", wind, sun, water, gravity, velocity)
}
case class FlowerCellX(val index: Int = Generator.get(), val neighbours: Map[String, CellX] = Map[String, CellX]().empty, wind: Double, sun: Double, water: Double, gravity: Double, velocity: (Double, Double, Double)) extends CellX {
  override val state: State = FlowerState("FS", wind, sun, water, gravity, velocity)
}
