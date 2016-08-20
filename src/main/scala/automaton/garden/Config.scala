package automaton.garden

import automaton.Vect

import scala.collection.mutable
import scala.util.Random

/**
  * Created by Basil on 13/07/2016.
  */
object Config {

  /** Basic 3 * 3 * 3 Garden **/

  private val p1 = List(EarthState(s = "ES" // 1
    , wind = 0.0
    , sun = 0.0
    , water = 1.0
    , gravity = 1.0
    , velocity = Vect(0.0, 0.0, 0.0)
    , age = 0
    , volume = 1)
    , EarthState(s = "ES" // 2
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , EarthState(s = "ES" // 3
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , EarthState(s = "ES" // 4
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , EarthState(s = "ES" // 5
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , EarthState(s = "ES" // 6
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , EarthState(s = "ES" // 7
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , EarthState(s = "ES" // 8
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , EarthState(s = "ES" // 9
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1))

  private val p2 = List(GrassState(s = "GS" // 1
    , wind = 0.0
    , sun = 0.0
    , water = 1.0
    , gravity = 1.0
    , velocity = Vect(0.0, 0.0, 0.0)
    , age = 0
    , volume = 1)
    , GrassState(s = "GS" // 2
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , GrassState(s = "GS" // 3
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , GrassState(s = "GS" // 4
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , PlantState(s = "PS" // 5
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 1.0, 0.0)
      , age = 0
      , volume = 1)
    , GrassState(s = "GS" // 6
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , GrassState(s = "GS" // 7
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , GrassState(s = "GS" // 8
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , GrassState(s = "GS" // 9
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1))

  private val p3 = List(SkyState(s = "SS" // 1
    , wind = 0.0
    , sun = 0.0
    , water = 1.0
    , gravity = 1.0
    , velocity = Vect(0.0, 0.0, 0.0)
    , age = 0
    , volume = 1)
    , SkyState(s = "SS" // 2
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , SkyState(s = "SS" // 3
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , SkyState(s = "SS" // 4
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , SkyState(s = "SS" // 5
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , SkyState(s = "SS" // 6
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , SkyState(s = "SS" // 7
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , SkyState(s = "SS" // 8
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1)
    , SkyState(s = "SS" // 9
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = Vect(0.0, 0.0, 0.0)
      , age = 0
      , volume = 1))

  def basic333 = p1 ::: p2 ::: p3

  /** auto automaton.garden - flat with plant only **/

  def autoBasicFlat(x :Int, y: Int, z: Int, seeds: List[Int]): List[State] = {

    val l = for {
      xs <- (0 until x * y * z)
    } yield {
      if (seeds.contains(xs)) PlantState(s = "PS"
                                        , wind = 0.0
                                        , sun = 0.0
                                        , water = 1.0
                                        , gravity = 1.0
                                        , velocity = Vect(0.0, 0.0, 1.0)
                                        , age = 0
                                        , volume = 1)
      else if (xs < x * y) EarthState(s = "ES"
                                , wind = 0.0
                                , sun = 0.0
                                , water = 1.0
                                , gravity = 1.0
                                , velocity = Vect(0.0, 0.0, 0.0)
                                , age = 0
                                , volume = 1)
      else if (xs >= x * y && xs < 2 * x * y) GrassState(s = "GS"
                                                        , wind = 0.0
                                                        , sun = 0.0
                                                        , water = 1.0
                                                        , gravity = 1.0
                                                        , velocity = Vect(0.0, 0.0, 0.0)
                                                        , age = 0
                                                        , volume = 1)
      else SkyState(s = "SS"
                    , wind = 0.0
                    , sun = 0.0
                    , water = 1.0
                    , gravity = 1.0
                    , velocity = Vect(0.0, 0.0, 0.0)
                    , age = 0
                    , volume = 1)
    }

    l.toList

  }

  def autoBasicRandom(x :Int, y: Int, z: Int): List[State] = {

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
        EarthState(s = "ES"
          , wind = 0.0
          , sun = 0.0
          , water = 1.0
          , gravity = 1.0
          , velocity = Vect(0.0, 0.0, 0.0)
          , age = 0
          , volume = 1)
      } else if (h == 0) {
        m((xs, ys)) -= 1

        if (r.nextInt % 10 == 0) PlantState(s = "PS"
          , wind = 0.0
          , sun = 0.0
          , water = 1.0
          , gravity = 1.0
          , velocity = Vect(0.0, 0.0, 1.0)
          , age = 0
          , volume = 1)
        else GrassState(s = "GS"
          , wind = 0.0
          , sun = 0.0
          , water = 1.0
          , gravity = 1.0
          , velocity = Vect(0.0, 0.0, 0.0)
          , age = 0
          , volume = 1)
      } else {
        if ((zs == 40 || zs == 41 || zs == 42) && (ys == 6 || ys == 7 || ys == 8) && (xs == 2 || xs == 3 || xs == 4)) SunState(s = "NS"
          , wind = 0.0
          , sun = 0.0
          , water = 1.0
          , gravity = 1.0
          , velocity = Vect(0.0, 0.0, 0.0)
          , age = 0
          , volume = 1)
        else if ((zs == 31 || zs == 32) && r.nextInt % 4 == 0) SkyState(s = "CS"
          , wind = 0.0
          , sun = 0.0
          , water = 1.0
          , gravity = 1.0
          , velocity = Vect(0.0, 0.0, 0.0)
          , age = 0
          , volume = 1)
        else SkyState(s = "SS"
          , wind = 0.0
          , sun = 0.0
          , water = 1.0
          , gravity = 1.0
          , velocity = Vect(0.0, 0.0, 0.0)
          , age = 0
          , volume = 1)
      }

    }

    l.toList

  }

  def allOn(x: Int, y: Int, z: Int): List[State] = {

    val l = for {
      xs <- (0 until x * y * z)
    } yield RedState("R", Vect(0.0,0.0,0.0))

    l.toList

  }

  def classic(x: Int, y: Int, z: Int): List[State] = {

    val on = List((x * y * z) / 2)

    val l = for {
      xs <- (0 until x * y * z)
    } yield {
      if (on.contains(xs)) RedState("R", Vect(0.0,0.0,0.0))
      else PadState("P", Vect(0.0,0.0,0.0))
    }

    l.toList

  }

}
