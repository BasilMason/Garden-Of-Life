package newauto

import scala.util.Random

/**
  * Created by Basil on 31/08/2016.
  */
case object TransitionBuilder {

  def ruleX(i: Int): (NState, Neighbours) => NState = (s, ns) => {

    require(i < 256, "Classical must be below 256")

    def stateToDigit(binaryState: NState): Int = binaryState match {
      case NAlive => 1
      case NDead => 0
    }

    def digitToState(c: Int): NState = c match {
      case '1' => NAlive
      case '0' => NDead
    }

    val bin = List.fill(8 - i.toBinaryString.length)("0").mkString + i.toBinaryString
    val states = (0 until 8).map(n => List.fill(3 - n.toBinaryString.length)("0").mkString + n.toBinaryString)
    val statesWithIndex = states zipWithIndex
    val statesMap = statesWithIndex toMap

    val l = ns("LEFT")
    val m = s
    val r = ns("RIGHT")
    val p = Vector(stateToDigit(l.currentState), stateToDigit(m), stateToDigit(r.currentState)).mkString
    val n = statesMap(p)
    digitToState(bin.charAt(n))

  }

  def threeDimBasic: (NState, Neighbours) => NState = (s, ns) => {

    val l = ns("LEFT")
    val r = ns("RIGHT")
    val t = ns("TOP")
    val b = ns("BOTTOM")
    val bb = ns("BACK")
    val f = ns("FRONT")

    if (l.currentState == NAlive || t.currentState == NAlive || b.currentState == NAlive || r.currentState == NAlive || bb.currentState == NAlive || f.currentState == NAlive) NAlive
    else NDead

  }

  def basicSoil: (NState, Neighbours) => NState = (s, ns) => {

    val waterUsage = ns.map(p => p._2.currentState match {
      case NGrass(wa, sn, wi, vl, ag, vm) => 1
      case NPlant(wa, sn, wi, vl, ag, vm) => 2
      case _ => 0
    }).sum

    s match {
      case NSoil(wa, sn, wi) => NSoil(wa - waterUsage, sn, wi)
    }

  }

  def basicSky: (NState, Neighbours) => NState = (s, ns) => {
    val d = ns.getOrElse("BOTTOM", NewCell(NDead, (s, ns) => s))

    d.currentState match {
      case NPlant(wa, sn, wi, vl, ag, vm) => NPlant(wa, sn, wi, vl, ag, vm)
      case _ => s
    }
  }

  // dynamic states?!?




  def dynamicSky: (NState, Neighbours) => NState = (s, ns) => {

    val r = Random
    val n = r.nextInt

    val influencers = ns.map(p => {
      val infl = influencesMe2(p._1, stateToVector(p._2.currentState))
      (p._1, infl._1, infl._2)
    })


    val strongInfluencers = influencers.filter(p => p._2 == true).toList

    val theStrongest = strongestInfluencer2(strongInfluencers)

    if (theStrongest._1 == "NONE") s
    else {
      val strongest = ns(theStrongest._1)

      strongest.currentState match {
        case NPlant(wa, sn, wi, vl, ag, vm) => NPlant(wa, sn, wi, vl, ag, vm)
        case NGrass(wa, sn, wi, vl, ag, vm) => n % 30 match {
          case 0 => NGrass(wa, sn, wi, vl, ag + 1, vm)
          case _ => NSky(wa, sn, wi)
        }

        case NTree(wa, sn, wi, vl, ag, vm) => {

          if (List("BOTTOM").contains(theStrongest._1)) NTree(wa, sn, wi, List(Vector3(3 + (n % 3),0,3 + (n % 3)),Vector3(-3 - (n % 3),0,-3 - (n % 3))), ag, vm)
          else if (List("LEFT", "RIGHT", "BACK", "FRONT").contains(theStrongest._1) && theStrongest._2.x == 0 && theStrongest._2.z == 0) NTree(wa, sn, wi, vl.map(v => Vector3(0, 3, 0)), ag, vm)
          else if (List("LEFT", "RIGHT", "BACK", "FRONT").contains(theStrongest._1)) NTree(wa, sn, wi, vl.map(v => Vector3(v.x-1, 0, v.z-1)), ag, vm)
          else s

        }
        case _ => s
      }
    }

  }

  def basicGrass: (NState, Neighbours) => NState = (s, ns) => s match {
    case NGrass(wa, sn, wi, vl, ag, vm) => NGrass(wa, sn, wi, vl, ag + 1, vm)
  }
  def basicPlant: (NState, Neighbours) => NState = (s, ns) => s
  def basicTree: (NState, Neighbours) => NState = (s, ns) => s match {
    case NTree(wa, sn, wi, vl, ag, vm) => NTree(wa, sn, wi, vl.map(v => Vector3(v.x,v.y + 1,v.z)), ag + 1, vm)
  }

//  def strongestInfluencer(ns: Neighbours): String = {
//
//    var m = 0.0
//    var s = "NONE"
//
//    for {
//      ks <- ns.keySet
//    } {
//
//      val v = stateToVector(ns(ks).currentState)
//
//      if (v.x == 0 && v.y == 0 && v.z == 0) s
//      else ks match {
//
//        case "LEFT" => if (Math.abs(stateToVector(ns(ks).currentState).x) > m) {m = Math.abs(stateToVector(ns(ks).currentState).x); s = "LEFT"}
//        case "RIGHT" => if (Math.abs(stateToVector(ns(ks).currentState).x) > m) {m = Math.abs(stateToVector(ns(ks).currentState).x); s = "RIGHT"}
//        case "FRONT" => if (Math.abs(stateToVector(ns(ks).currentState).z) > m) {m = Math.abs(stateToVector(ns(ks).currentState).z); s = "FRONT"}
//        case "BACK" => if (Math.abs(stateToVector(ns(ks).currentState).z) > m) {m = Math.abs(stateToVector(ns(ks).currentState).z); s = "BACK"}
//        case "BOTTOM" => if (Math.abs(stateToVector(ns(ks).currentState).y) > m) {m = Math.abs(stateToVector(ns(ks).currentState).y); s = "BOTTOM"}
//        case "TOP" => if (Math.abs(stateToVector(ns(ks).currentState).y) > m) {m = Math.abs(stateToVector(ns(ks).currentState).y); s = "TOP"}
//        case _ => ;
//      }
//
//    }
//
//    s
//
//  }

  def strongestInfluencer2(ns: List[(String, Boolean, Vector3)]): (String,Vector3) = {

    var strongest = 0
    var strongestVector = Vector3(0,0,0)
    var site = "NONE"

    for {
      n <- ns
    } {

      n._1 match {
        case "LEFT" if Math.abs(n._3.x) > Math.abs(strongest) => {
          strongest = n._3.x
          site = "LEFT"
          strongestVector = n._3
        }
        case "RIGHT" if Math.abs(n._3.x) > Math.abs(strongest) => {
          strongest = n._3.x
          site = "RIGHT"
          strongestVector = n._3
        }
        case "TOP" if Math.abs(n._3.y) > Math.abs(strongest) => {
          strongest = n._3.y
          site = "TOP"
          strongestVector = n._3
        }
        case "BOTTOM" if Math.abs(n._3.y) > Math.abs(strongest) => {
          strongest = n._3.y
          site = "BOTTOM"
          strongestVector = n._3
        }
        case "BACK" if Math.abs(n._3.z) > Math.abs(strongest) => {
          strongest = n._3.z
          site = "BACK"
          strongestVector = n._3
        }
        case "FRONT" if Math.abs(n._3.z) > Math.abs(strongest) => {
          strongest = n._3.z
          site = "FRONT"
          strongestVector = n._3
        }
        case _ => ;

      }

    }

    (site, strongestVector)

  }

  def stateToVector(s: NState): List[Vector3] = s match {
    case DynamicNState(water, sunlight, wind, velocity, age, volume) => velocity match {
      case x :: xs => velocity
      case _ => List(Vector3(0,0,0))
    }
    case _ => List(Vector3(0,0,0))
  }

  def influencesMe(site: String, v: List[Vector3]): Boolean = {

    var infl = false

    for {
      vs <- v
    } {
      if (!infl) {
        infl = site match {
          case "LEFT" if vs.x > 0 => true
          case "RIGHT" if vs.x < 0 => true
          case "FRONT" if vs.z > 0 => true
          case "BACK" if vs.z < 0 => true
          case "BOTTOM" if vs.y > 0 => true
          case "TOP" if vs.y < 0 => true
          case _ => false
        }
      }
    }

    infl

  }

  def influencesMe2(site: String, v: List[Vector3]): (Boolean, Vector3) = {

    var infl = false
    var infls = Vector3(0,0,0)

    for {
      vs <- v
    } {
      if (!infl) {
        site match {
          case "LEFT" if vs.x > 0 => {
            infl = true
            infls = vs
          }
          case "RIGHT" if vs.x < 0 => {
            infl = true
            infls = vs
          }
          case "FRONT" if vs.z > 0 => {
            infl = true
            infls = vs
          }
          case "BACK" if vs.z < 0 => {
            infl = true
            infls = vs
          }
          case "BOTTOM" if vs.y > 0 => {
            infl = true
            infls = vs
          }
          case "TOP" if vs.y < 0 => {
            infl = true
            infls = vs
          }
          case _ => {
            infl = false
          }
        }
      }
    }

    (infl,infls)

  }

}
