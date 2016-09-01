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

}
