package automaton.automata

import automaton.garden._

import scala.util.Random

/**
  * Created by Basil on 17/08/2016.
  */
case class Randomizer(init: List[State]) extends Automata with RandomGenerator {
  override def next: List[State] = init.map(s => rule(s, Map.empty))
}
