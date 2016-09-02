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

  def flat(x: Int, y: Int, z: Int, s: List[Vector3]): List[NCell] = {

    val cf = NCellFactory

    val l = for {
      zs <- (0 until z)
      ys <- (0 until y)
      xs <- (0 until x)
      v = Vector3(xs, ys, zs)
    } yield {
      if (s.contains(v)) cf.getNCellGardenPlant
      else if (ys < 1) cf.getNCellGardenSoil
      else if (ys == 1) cf.getNCellGardenGrass
      else cf.getNCellGardenSky
    }

    l.toList

  }

//  def autoBasicFlat(x :Int, y: Int, z: Int, seeds: List[Int]): List[State] = {
//
//    val l = for {
//      xs <- (0 until x * y * z)
//    } yield {
//      if (seeds.contains(xs)) PlantState(wind = 0.0
//        , sun = 0.0
//        , water = 1.0
//        , gravity = 1.0
//        , velocity = Vect(0.0, 0.0, 1.0)
//        , age = 0
//        , volume = 1)
//      else if (xs < x * y) EarthState(wind = 0.0
//        , sun = 0.0
//        , water = 1.0
//        , gravity = 1.0
//        , velocity = Vect(0.0, 0.0, 0.0)
//        , age = 0
//        , volume = 1)
//      else if (xs >= x * y && xs < 2 * x * y) GrassState(wind = 0.0
//        , sun = 0.0
//        , water = 1.0
//        , gravity = 1.0
//        , velocity = Vect(0.0, 0.0, 0.0)
//        , age = 0
//        , volume = 1)
//      else SkyState(wind = 0.0
//        , sun = 0.0
//        , water = 1.0
//        , gravity = 1.0
//        , velocity = Vect(0.0, 0.0, 0.0)
//        , age = 0
//        , volume = 1)
//    }
//
//    l.toList
//
//  }

}
