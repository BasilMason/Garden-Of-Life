package automaton.automata

import automaton.garden.State

/**
  * Created by Basil on 13/07/2016.
  */
trait Automata {
  def next: List[State]
}

