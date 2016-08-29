package automaton.automata

import automaton.garden._

import scala.util.Random

/**
  * Created by Basil on 17/08/2016.
  */
case class Randomizer(init: List[BinaryState]) extends ClassicalAutomaton with RandomGenerator {
  override def next: List[BinaryState] = init.map(s => rule(s, Map.empty))
}
