package newauto

/**
  * Created by Basil on 31/08/2016.
  */
case object NConfig {

  def classical(x: Int): List[NCell] = {

    val m = x / 2

    val l = for {
      xs <- (0 until x)
    } yield {
      if (xs == m) NCellFactory.getNCellRule90Alive
      else NCellFactory.getNCellRule90Dead
    }

    l.toList

  }

  def classical(x: Int, y: Int, z: Int): List[NCell] = {

    val on = List((x * y * z) / 2)

    val l = for {
      xs <- (0 until x * y * z)
    } yield {
      if (on.contains(xs)) NCellFactory.getNCellBasic3dAlive
      else NCellFactory.getNCellBasic3dDead
    }

    l.toList

  }

}
