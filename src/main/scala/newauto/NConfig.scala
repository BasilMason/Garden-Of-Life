package newauto

import automaton.garden.Noise

import scala.collection.mutable
import scala.util.Random

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
      if (s.contains(v)) cf.getNCellGardenTree
      else if (zs < 1) cf.getNCellGardenSoil
      else if (zs == 1) cf.getNCellGardenGrass
      else cf.getNCellGardenSky
    }

    l.toList

  }

  def rugged(x: Int, y: Int, z: Int): List[NCell] = {

    val cf = NCellFactory
    val r = Random
    val amp = 7
    val noise = Noise.getNoise(x, y, amp)

    val m: mutable.Map[(Int, Int), Int] = mutable.Map(noise: _*)

    val l = for {
      zs <- (0 until z)
      ys <- (0 until y)
      xs <- (0 until x)
    } yield {

      val h = m.getOrElse((xs, ys), -1)

      if (h > 0) {
        m((xs, ys)) -= 1
        cf.getNCellGardenSoil
      } else if (h == 0) {
        m((xs, ys)) -= 1

        if (r.nextInt % 21 == 0) cf.getNCellGardenTree
        else if (r.nextInt % 10 == 0) cf.getNCellGardenPlant
        else cf.getNCellGardenGrass


      } else cf.getNCellGardenSky

    }

    l.toList

  }

}
