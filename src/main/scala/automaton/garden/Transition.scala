package automaton.garden

import automaton.{Neighbours, Vect}

import scala.util.Random

/**
  * Created by Basil on 07/08/2016.
  *
  *
  */
sealed trait Transition {
  def rule(current: State, ns: Neighbours): State
  protected def strongestInfluencer(ns: Neighbours): String = {

    var m = 0.0
    var s = "NONE"

    for {
      ks <- ns.keySet
    } ks match {
      case "LEFT" => if (Math.abs(ns(ks).velocity.x) > m) {m = Math.abs(ns(ks).velocity.x); s = "LEFT"}
      case "RIGHT" => if (Math.abs(ns(ks).velocity.x) > m) {m = Math.abs(ns(ks).velocity.x); s = "RIGHT"}
      case "FRONT" => if (Math.abs(ns(ks).velocity.y) > m) {m = Math.abs(ns(ks).velocity.y); s = "FRONT"}
      case "BACK" => if (Math.abs(ns(ks).velocity.y) > m) {m = Math.abs(ns(ks).velocity.y); s = "BACK"}
      case "BOTTOM" => if (Math.abs(ns(ks).velocity.z) > m) {m = Math.abs(ns(ks).velocity.z); s = "BOTTOM"}
      case "TOP" => if (Math.abs(ns(ks).velocity.z) > m) {m = Math.abs(ns(ks).velocity.z); s = "TOP"}
      case _ => ;
    }

    s

  }

  protected def influencesMe(site: String, v: Vect): Boolean = {
    site match {
      case "LEFT" if v.x > 0 => true
      case "RIGHT" if v.x > 0 => true
      case "FRONT" if v.y > 0 => true
      case "BACK" if v.y > 0 => true
      case "BOTTOM" if v.z > 0 => true
      case "TOP" if v.z < 0 => true
      case _ => false
    }
  }
}

trait GameOfLife extends Transition {

  override def rule(current: State, ns: Neighbours): State = {

    val ss = ns.values.toList
    val cs = ss.map(s => s match {
      case RedState(st, v) => 1
      case _ => 0
    }).sum

    cs match {
      case n if n > 0 && n < 3 => RedState("R", Vect(0.0, 0.0, 0.0))
      case _ => PadState("P", Vect(0.0, 0.0, 0.0))
    }

  }

}

trait BasicGarden extends Transition {
  override def rule(current: State, ns: Neighbours): State = {

    current match {

      // Sky
      case SkyState(s, wi, sn, wa, g, v, a, vlm) => {
        ns.get("BOTTOM") match {
          case None => current
          case Some(c) => c match {
            case PlantState(s, wi, sn, wa, g, v, a, vlm) if v.z < 0.6 => {
              FlowerState("FS", wi, sn, wa, g, v, a + 1, vlm)
            }
            case PlantState(s, wi, sn, wa, g, v, a, vlm) => PlantState(s, wi, sn, wa, g, Vect(v.x, v.y, v.z - 0.1), a + 1, vlm)
            case FlowerState(s, wi, sn, wa, g, v, a, vlm) => FlowerState(s, wi, sn, wa, g, v, a + 1, vlm)
            case SkyState(s, wi, sn, wa, g, v, a, vlm) => {
              ns.get("LEFT") match {
                case None => current
                case Some(c) => c match {
                  case FlowerState(s, wi, sn, wa, g, v, a, vlm) => FlowerState(s, wi, sn, wa, g, v, a + 1, vlm)
                  case _ => current
                }
              }
            }
            case _ => current
          }
        }
      }
      case PlantState(s, wi, sn, wa, g, v, a, vlm) => PlantState(s, wi, sn, wa, g, Vect(v.x, v.y, v.z - 0.1), a + 1, vlm)

      case FlowerState(s, wi, sn, wa, g, v, a, vlm) => FlowerState(s, wi, sn, wa, g, v, a + 1, vlm)

      case _ => current

    }

  }

}

trait RandomGenerator extends Transition {
  override def rule(current: State, ns: Neighbours): State = {
    current match {
      case RedState(st, v) => GreenState("G", Vect(0.0, 0.0, 0.0))
      case PadState(st, v) => {
        val r = Random
        if (r.nextInt > 0) BlueState("B", Vect(0.0, 0.0, 0.0))
        else PadState(st, v)
      }
      case _ => RedState("R", Vect(0.0, 0.0, 0.0))
    }
  }
}

trait AllNeighbours extends Transition {
  override def rule(current: State, ns: Neighbours): State = {

    val influencers = ns.filter(p => influencesMe(p._1, p._2.velocity))
    val strongest = strongestInfluencer(influencers)

    current match {
      case SkyState(s, wi, sn, wa, g, v, a, vlm) => {
        ns.get(strongest) match {
          case None => current
          case Some(s) => s match {
            case PlantState(s, wi, sn, wa, g, v, a, vlm) => {

              val nvel = if (a > 1) Vect(v.x + 0.2, v.y + 0.2, v.z - 0.2)
              else Vect(v.x, v.y, v.z - 0.2)

              PlantState(s, wi, sn, wa, g, nvel, a + 1, vlm)
            }
          }
        }
      }
      case PlantState(s, wi, sn, wa, g, v, a, vlm) => {

        a match {
          case n if n > 3 => FlowerState("FS", wi, sn, wa, g, v, a, vlm)
          case _ => PlantState(s, wi, sn, wa, g, v, a, vlm)
        }

      }
      case _ => current
    }


  }
}

