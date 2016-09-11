package newauto

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

    val influencers = ns.filter(p => influencesMe(p._1, ((p._2.currentState.asInstanceOf[DynamicNState]).velocity.asInstanceOf[Vector3])))
    val strongestSite = strongestInfluencer(influencers)
    val strongest = ns(strongestSite)

    strongest.currentState match {
      case NPlant(wa, sn, wi, vl, ag, vm) => NPlant(wa, sn, wi, vl, ag, vm)
      case NTree(wa, sn, wi, vl, ag, vm) => ag % 3 match {
        case 0 if List("LEFT", "RIGHT", "BACK", "FRONT").contains(strongestSite) => NTree(wa, sn, wi, vl, ag, vm)
        case _ => s
      }
      case _ => s
    }
  }

  def basicGrass: (NState, Neighbours) => NState = (s, ns) => s
  def basicPlant: (NState, Neighbours) => NState = (s, ns) => s
  def basicTree: (NState, Neighbours) => NState = (s, ns) => s match {
    case NTree(wa, sn, wi, vl, ag, vm) => NTree(wa, sn, wi, vl, ag + 1, vm)
  }

  def strongestInfluencer(ns: Neighbours): String = {

    var m = 0.0
    var s = "NONE"

    for {
      ks <- ns.keySet
    } ks match {
      case "LEFT" => if (Math.abs(ns(ks).asInstanceOf[DynamicNState].velocity.asInstanceOf[Vector3].x) > m) {m = Math.abs(ns(ks).asInstanceOf[DynamicNState].velocity.asInstanceOf[Vector3].x); s = "LEFT"}
      case "RIGHT" => if (Math.abs(ns(ks).asInstanceOf[DynamicNState].velocity.asInstanceOf[Vector3].x) > m) {m = Math.abs(ns(ks).asInstanceOf[DynamicNState].velocity.asInstanceOf[Vector3].x); s = "RIGHT"}
      case "FRONT" => if (Math.abs(ns(ks).asInstanceOf[DynamicNState].velocity.asInstanceOf[Vector3].y) > m) {m = Math.abs(ns(ks).asInstanceOf[DynamicNState].velocity.asInstanceOf[Vector3].y); s = "FRONT"}
      case "BACK" => if (Math.abs(ns(ks).asInstanceOf[DynamicNState].velocity.asInstanceOf[Vector3].y) > m) {m = Math.abs(ns(ks).asInstanceOf[DynamicNState].velocity.asInstanceOf[Vector3].y); s = "BACK"}
      case "BOTTOM" => if (Math.abs(ns(ks).asInstanceOf[DynamicNState].velocity.asInstanceOf[Vector3].z) > m) {m = Math.abs(ns(ks).asInstanceOf[DynamicNState].velocity.asInstanceOf[Vector3].z); s = "BOTTOM"}
      case "TOP" => if (Math.abs(ns(ks).asInstanceOf[DynamicNState].velocity.asInstanceOf[Vector3].z) > m) {m = Math.abs(ns(ks).asInstanceOf[DynamicNState].velocity.asInstanceOf[Vector3].z); s = "TOP"}
      //      case "LEFT" => ns.get(ks).get.velocity
      case _ => ;
    }

    s

  }

  def influencesMe(site: String, v: Vector3): Boolean = {
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
