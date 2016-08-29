package automaton.garden

import automaton.{BNeighbours, GNeighbours, Neighbours, Vect}

import scala.util.Random

/**
  * Created by Basil on 07/08/2016.
  *
  *
  */
sealed trait Transition {

}

trait Pattern extends Transition {
  def rule(current: BinaryState, ns: BNeighbours): BinaryState
}
trait Life extends Transition {
  def rule(current: BinaryState, ns: Neighbours): BinaryState
}

trait Growth extends Transition {
  def rule(current: GardenState, ns: GNeighbours): GardenState
  protected def strongestInfluencer(ns: GNeighbours): String = {

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
//      case "LEFT" => ns.get(ks).get.velocity
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

trait GameOfLife extends Life {

  override def rule(current: BinaryState, ns: Neighbours): BinaryState = {

    val ss = ns.values.toList
    val cs = ss.map(s => s match {
      case RedState => 1
      case _ => 0
    }).sum

    cs match {
      case n if n > 0 && n < 3 => RedState
      case _ => PadState
    }

  }

}

trait BasicGarden extends Growth {
  override def rule(current: GardenState, ns: GNeighbours): GardenState = {

    current match {

      // Sky
      case SkyState(wi, sn, wa, g, v, a, vlm) => {
        ns.get("BOTTOM") match {
          case None => current
          case Some(c) => c match {
            case PlantState(wi, sn, wa, g, v, a, vlm) if v.z < 0.6 => {
              FlowerState(wi, sn, wa, g, v, a + 1, vlm)
            }
            case PlantState(wi, sn, wa, g, v, a, vlm) => PlantState(wi, sn, wa, g, Vect(v.x, v.y, v.z - 0.1), a + 1, vlm)
            case FlowerState(wi, sn, wa, g, v, a, vlm) => FlowerState(wi, sn, wa, g, v, a + 1, vlm)
            case SkyState(wi, sn, wa, g, v, a, vlm) => {
              ns.get("LEFT") match {
                case None => current
                case Some(c) => c match {
                  case FlowerState(wi, sn, wa, g, v, a, vlm) => FlowerState(wi, sn, wa, g, v, a + 1, vlm)
                  case _ => current
                }
              }
            }
            case _ => current
          }
        }
      }
      case PlantState(wi, sn, wa, g, v, a, vlm) => PlantState(wi, sn, wa, g, Vect(v.x, v.y, v.z - 0.1), a + 1, vlm)

      case FlowerState(wi, sn, wa, g, v, a, vlm) => FlowerState(wi, sn, wa, g, v, a + 1, vlm)

      case _ => current

    }

  }

}

trait RandomGenerator extends Life {
  override def rule(current: BinaryState, ns: Neighbours): BinaryState = {
    current match {
      case RedState => GreenState
      case PadState => {
        val r = Random
        if (r.nextInt > 0) BlueState
        else PadState
      }
      case _ => RedState
    }
  }
}

trait AllNeighbours extends Growth {
  override def rule(current: GardenState, ns: GNeighbours): GardenState = {

    val influencers = ns.filter(p => influencesMe(p._1, p._2.velocity))

    val strongest = strongestInfluencer(influencers)

    current match {
      case SkyState(wi, sn, wa, g, v, a, vlm) => {
        ns.get(strongest) match {
          case None => current
          case Some(s) => s match {
            case PlantState(wi, sn, wa, g, v, a, vlm) => {

              val nvel = if (a > 1) Vect(v.x + 0.2, v.y + 0.2, v.z - 0.2)
              else Vect(v.x, v.y, v.z - 0.2)

              PlantState(wi, sn, wa, g, nvel, a + 1, vlm)
            }
            case CloudState(wi, sn, wa, g, v, a, vlm) => CloudState(wi, sn, wa, g, v, a, vlm)
            case _ => current
          }
        }
      }
      case PlantState(wi, sn, wa, g, v, a, vlm) => {

        a match {
          case n if n > 3 => FlowerState(wi, sn, wa, g, v, a, vlm)
          case _ => PlantState(wi, sn, wa, g, v, a, vlm)
        }

      }
      case CloudState(wi, sn, wa, g, v, a, vlm) => SkyState(wi, sn, wa, g, v, a, vlm)
      case _ => current
    }


  }
}

trait Rule90 extends Pattern {

  val numb = 90
  val bin = List.fill(8 - numb.toBinaryString.length)("0").mkString + numb.toBinaryString
  val states = (0 until 8).map(n => List.fill(3 - n.toBinaryString.length)("0").mkString + n.toBinaryString)
  val statesWithIndex = states zipWithIndex
  val statesMap = statesWithIndex toMap

  def rule(current: BinaryState, ns: BNeighbours): BinaryState = {

    val l = ns.get("LEFT").get
    val m = current
    val r = ns.get("RIGHT").get
    val p = Vector(stateToDigit(l), stateToDigit(m), stateToDigit(r)).mkString
    val n = statesMap(p)
    digitToState(bin.charAt(n))

  }

  // put on BinaryState?
  private def stateToDigit(binaryState: BinaryState): Int = binaryState match {
    case Alive => 1
    case Dead => 0
  }

  private def digitToState(c: Int): BinaryState = c match {
    case '1' => Alive
    case '0' => Dead
  }

}

