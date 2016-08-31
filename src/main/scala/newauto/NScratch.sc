sealed trait VectorN
case class Vector1(x: Int) extends VectorN
case class Vector2(x: Int, y: Int) extends VectorN
case class Vector3(x: Int, y: Int, z: Int) extends VectorN
trait NCell {
  def currentState: NState
  def transitionFunction: Transition
}

case class NewCell(state: NState, rule: Transition) extends NCell {
  override def currentState = state
  override def transitionFunction: Transition = rule
}
trait NState
trait BinaryNState extends NState
trait GardenNState extends NState
case object Dead extends BinaryNState
case object Alive extends BinaryNState
type Site = String
type Grid = Map[VectorN, NCell]
type Neighbours = Map[Site, NCell]
type Neighbourhood = Map[VectorN, Neighbours]
type Transition = (NState, Neighbours) => NState

def ruleX(i: Int): (NState, Neighbours) => NState = (s, ns) => {

  require(i < 256, "Classical must be below 256")

  def stateToDigit(binaryState: NState): Int = binaryState match {
    case Alive => 1
    case Dead => 0
  }

  def digitToState(c: Int): NState = c match {
    case '1' => Alive
    case '0' => Dead
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

val r = ruleX(90)
val cur = Dead
val ns = Map("LEFT" -> NewCell(Dead, (s,nss) => s), "RIGHT" -> NewCell(Alive, (s,nss) => s))
val n1 = r(cur, ns)

