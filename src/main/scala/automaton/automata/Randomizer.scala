package automaton.automata

import automaton.garden._

import scala.util.Random

/**
  * Created by Basil on 17/08/2016.
  */
case class Randomizer(init: List[State]) extends Automata {
  override def next: List[State] = init.map(s => s match {
    case RedState(st) => GreenState("G")
    case PadState(st) => {
      val r = Random
      if (r.nextInt > 0) BlueState("B")
      else PadState(st)
    }
    case _ => RedState("R")
  })
}
