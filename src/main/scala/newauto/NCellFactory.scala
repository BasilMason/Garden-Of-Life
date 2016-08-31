package newauto

/**
  * Created by Basil on 31/08/2016.
  */
case object NCellFactory {

  private val rule90 = TransitionBuilder.ruleX(90)

  def getNCellRule90Alive: NCell = NewCell(NAlive, rule90)
  def getNCellRule90Dead: NCell = NewCell(NDead, rule90)

}
