package garden

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
    , velocity = (0.0, 0.0, 0.0))
    , EarthState(s = "ES" // 2
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , EarthState(s = "ES" // 3
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , EarthState(s = "ES" // 4
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , EarthState(s = "ES" // 5
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , EarthState(s = "ES" // 6
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , EarthState(s = "ES" // 7
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , EarthState(s = "ES" // 8
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , EarthState(s = "ES" // 9
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0)))

  private val p2 = List(GrassState(s = "GS" // 1
    , wind = 0.0
    , sun = 0.0
    , water = 1.0
    , gravity = 1.0
    , velocity = (0.0, 0.0, 0.0))
    , GrassState(s = "GS" // 2
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , GrassState(s = "GS" // 3
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , GrassState(s = "GS" // 4
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , PlantState(s = "PS" // 5
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 1.0, 0.0))
    , GrassState(s = "GS" // 6
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , GrassState(s = "GS" // 7
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , GrassState(s = "GS" // 8
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , GrassState(s = "GS" // 9
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0)))

  private val p3 = List(SkyState(s = "SS" // 1
    , wind = 0.0
    , sun = 0.0
    , water = 1.0
    , gravity = 1.0
    , velocity = (0.0, 0.0, 0.0))
    , SkyState(s = "SS" // 2
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , SkyState(s = "SS" // 3
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , SkyState(s = "SS" // 4
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , SkyState(s = "SS" // 5
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , SkyState(s = "SS" // 6
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , SkyState(s = "SS" // 7
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , SkyState(s = "SS" // 8
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0))
    , SkyState(s = "SS" // 9
      , wind = 0.0
      , sun = 0.0
      , water = 1.0
      , gravity = 1.0
      , velocity = (0.0, 0.0, 0.0)))

  def basic333 = p1 ::: p2 ::: p3

  /** auto garden - flat with plant only **/

  def autoBasicFlat(x :Int, y: Int, z: Int, seeds: List[Int]): List[State] = {

    val l = for {
      xs <- (0 until x * y * z)
    } yield {
      if (seeds.contains(xs)) PlantState(s = "PS"
                                        , wind = 0.0
                                        , sun = 0.0
                                        , water = 1.0
                                        , gravity = 1.0
                                        , velocity = (0.0, 1.0, 0.0))
      else if (xs < x * y) EarthState(s = "ES"
                                , wind = 0.0
                                , sun = 0.0
                                , water = 1.0
                                , gravity = 1.0
                                , velocity = (0.0, 0.0, 0.0))
      else if (xs >= x * y && xs < 2 * x * y) GrassState(s = "GS"
                                                        , wind = 0.0
                                                        , sun = 0.0
                                                        , water = 1.0
                                                        , gravity = 1.0
                                                        , velocity = (0.0, 0.0, 0.0))
      else SkyState(s = "SS"
                    , wind = 0.0
                    , sun = 0.0
                    , water = 1.0
                    , gravity = 1.0
                    , velocity = (0.0, 0.0, 0.0))
    }

    l.toList

  }

  def allOn(x: Int, y: Int, z: Int): List[State] = {

    val l = for {
      xs <- (0 until x * y * z)
    } yield RedState("R")

    l.toList

  }

  def classic(x: Int, y: Int, z: Int): List[State] = {

    val on = List(36 + (81 * 4) + 5)

    val l = for {
      xs <- (0 until x * y * z)
    } yield {
      if (on.contains(xs)) RedState("R")
      else PadState("P")
    }

    l.toList

  }

}
