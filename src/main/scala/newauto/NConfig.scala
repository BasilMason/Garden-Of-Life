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

}
