package automaton.automata

import automaton.garden.{BinaryState, GardenState, State}

/**
  * Created by Basil on 13/07/2016.
  */
trait Automaton

trait ClassicalAutomaton extends Automaton {
  def next: List[BinaryState]
}

trait GardenAutomaton extends Automaton {
  def next: List[GardenState]
}