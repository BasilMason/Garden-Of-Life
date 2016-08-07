package garden

/**
  * Created by Basil on 13/07/2016.
  */
object Config {

  private val r1 = List(EarthState(s = "ES" // 1
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

  private val r2 = List(GrassState(s = "GS" // 1
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

  private val r3 = List(SkyState(s = "SS" // 1
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

  val cells = r1 ::: r2 ::: r3

}