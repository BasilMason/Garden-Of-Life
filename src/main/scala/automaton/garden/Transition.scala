package automaton.garden

import automaton.Neighbours

/**
  * Created by Basil on 07/08/2016.
  *
  *
  */
sealed trait Transition {
  def rule(current: State, ns: Neighbours): State
}

trait GameOfLife extends Transition {

  override def rule(current: State, ns: Neighbours): State = {

    val ss = ns.values.toList
    val cs = ss.map(s => s match {
      case RedState(st) => 1
      case _ => 0
    }).sum

    cs match {
      case n if n > 0 && n < 3 => RedState("R")
      case _ => PadState("P")
    }

  }

}

trait BasicGarden extends Transition {
  override def rule(current: State, ns: Neighbours): State = {

    current match {

      // Sky
      case SkyState(s, wi, sn, wa, g, v) => {
        ns.get("BOTTOM") match {
          case None => current
          case Some(c) => c match {
            case PlantState(s, wi, sn, wa, g, v) if v._3 < 0.6 => {
              FlowerState("FS", wi, sn, wa, g, v)
            }
            case PlantState(s, wi, sn, wa, g, v) => PlantState(s, wi, sn, wa, g, (v._1, v._2, v._3 - 0.1))
            case FlowerState(s, wi, sn, wa, g, v) => FlowerState(s, wi, sn, wa, g, v)
            case SkyState(s, wi, sn, wa, g, v) => {
              ns.get("LEFT") match {
                case None => current
                case Some(c) => c match {
                  case FlowerState(s, wi, sn, wa, g, v) => FlowerState(s, wi, sn, wa, g, v)
                  case _ => current
                }
              }
            }
            case _ => current
          }
        }
      }
      case PlantState(s, wi, sn, wa, g, v) => PlantState(s, wi, sn, wa, g, (v._1, v._2, v._3 - 0.1))

      case FlowerState(s, wi, sn, wa, g, v) => FlowerState(s, wi, sn, wa, g, v)

      case _ => current

    }

  }

}