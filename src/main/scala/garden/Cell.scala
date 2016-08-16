package garden

/**
  * Created by Basil on 13/07/2016.
  */
trait Cell {
  val state: State
  val index: Int
  val neighbours: Map[String, Cell]
}
case class RedCell(val index: Int = Generator.get()) extends Cell {
  override val state: State = RedState("R")
  override val neighbours = Map[String, Cell]().empty
}
case class RedCellWithNeighbour(val index: Int, val neighbours: Map[String, Cell]) extends Cell with Neighbourhood {
  override val state: State = RedState("R")
}
case class GreenCell(val index: Int = Generator.get()) extends Cell {
  override val state: State = RedState("G")
  override val neighbours = Map[String, Cell]().empty
}
case class GreenCellWithNeighbour(val index: Int, val neighbours: Map[String, Cell]) extends Cell with Neighbourhood {
  override val state: State = GreenState("G")
}
case class BlueCell(val index: Int = Generator.get()) extends Cell {
  override val state: State = BlueState("B")
  override val neighbours = Map[String, Cell]().empty
}
case class BlueCellWithNeighbour(val index: Int, val neighbours: Map[String, Cell]) extends Cell with Neighbourhood {
  override val state: State = BlueState("B")
}
case class PadCell(val index: Int = Generator.get()) extends Cell {
  override val state: State = PadState("P")
  override val neighbours = Map[String, Cell]().empty
}
case class PadCellWithNeighbour(val index: Int, val neighbours: Map[String, Cell]) extends Cell with Neighbourhood {
  override val state: State = PadState("P")
}
case class NilCell(val index: Int = Generator.get()) extends Cell {
  override val state: State = NilState("N")
  override val neighbours = Map[String, Cell]().empty
}
case class NilCellWithNeighbour(val index: Int, val neighbours: Map[String, Cell]) extends Cell with Neighbourhood {
  override val state: State = NilState("N")
}
case class SkyCell(val index: Int = Generator.get(), val neighbours: Map[String, Cell] = Map[String, Cell]().empty, wind: Double, sun: Double, water: Double, gravity: Double, velocity: (Double, Double, Double)) extends Cell {
  override val state: State = SkyState("SS", wind, sun, water, gravity, velocity)
}
case class GrassCell(val index: Int = Generator.get(), val neighbours: Map[String, Cell] = Map[String, Cell]().empty, wind: Double, sun: Double, water: Double, gravity: Double, velocity: (Double, Double, Double)) extends Cell {
  override val state: State = GrassState("GS", wind, sun, water, gravity, velocity)
}
case class EarthCell(val index: Int = Generator.get(), val neighbours: Map[String, Cell] = Map[String, Cell]().empty, wind: Double, sun: Double, water: Double, gravity: Double, velocity: (Double, Double, Double)) extends Cell {
  override val state: State = EarthState("ES", wind, sun, water, gravity, velocity)
}
case class PlantCell(val index: Int = Generator.get(), val neighbours: Map[String, Cell] = Map[String, Cell]().empty, wind: Double, sun: Double, water: Double, gravity: Double, velocity: (Double, Double, Double)) extends Cell {
  override val state: State = PlantState("PS", wind, sun, water, gravity, velocity)
}
case class FlowerCell(val index: Int = Generator.get(), val neighbours: Map[String, Cell] = Map[String, Cell]().empty, wind: Double, sun: Double, water: Double, gravity: Double, velocity: (Double, Double, Double)) extends Cell {
  override val state: State = FlowerState("FS", wind, sun, water, gravity, velocity)
}
