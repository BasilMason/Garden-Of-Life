package newauto

/**
  * Created by Basil on 31/08/2016.
  */
case object NCellFactory {

  private val rule90 = TransitionBuilder.ruleX(90)
  private val basic3d = TransitionBuilder.threeDimBasic

  def getNCellRule90Alive: NCell = NewCell(NAlive, rule90)
  def getNCellRule90Dead: NCell = NewCell(NDead, rule90)
  def getNCellBasic3dAlive: NCell = NewCell(NAlive, basic3d)
  def getNCellBasic3dDead: NCell = NewCell(NDead, basic3d)

  def getNCellGardenSoil: NCell = NewCell(NSoil(10.0, 1.0, 1.0), TransitionBuilder.basicSoil)
  def getNCellGardenSky: NCell = NewCell(NSky(10.0, 1.0, 1.0), TransitionBuilder.basicSky)
  def getNCellGardenGrass: NCell = NewCell(NGrass(10.0, 1.0, 1.0, Vector3(0, 0, 1), 0, 1), TransitionBuilder.basicGrass)
  def getNCellGardenPlant: NCell = NewCell(NPlant(10.0, 1.0, 1.0, Vector3(0, 0, 1), 0, 1), TransitionBuilder.basicPlant)

}
