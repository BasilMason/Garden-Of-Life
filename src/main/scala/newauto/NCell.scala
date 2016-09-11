package newauto

/**
  * Created by Basil on 31/08/2016.
  */
trait NCell {
  def currentState: NState
  def transitionFunction: Transition
}

case class NewCell(state: NState, rule: Transition) extends NCell {

  def this() {
    this(NDead, (s, ns) => s)
  }

  override def currentState = state
  override def transitionFunction: Transition = rule

}


