package automaton.automata
import automaton.garden.{BinaryState, Dead, Rule90}

/**
  * Created by Basil on 29/08/2016.
  */
case class OneDimensional(init: List[BinaryState], len: Int) extends ClassicalAutomaton with Rule90 {

  require(init.length == len, "Number of states provided must match dimensions")

  val initWithIndex = init.zipWithIndex
  val coordinateTable: Map[Int, BinaryState] = initWithIndex.map(p => (p._2, p._1)).toMap
  val neighbours: Map[Int, Map[String, BinaryState]] = coordinateTable.map(p => (p._1, p._1 match {
    case n if n == 0 => Map("LEFT" -> Dead, "RIGHT" -> coordinateTable(n + 1))
    case n if n == (len - 1) => Map("LEFT" -> coordinateTable(n - 1), "RIGHT" -> Dead)
    case n => Map("LEFT" -> coordinateTable(n - 1), "RIGHT" -> coordinateTable(n + 1))
  }))

  override def next: List[BinaryState] = {
    initWithIndex.map(p => rule(p._1, neighbours(p._2)))
  }
}
