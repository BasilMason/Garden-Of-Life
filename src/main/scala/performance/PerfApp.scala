package performance

import automaton.{Garden, ParGarden}
import org.scalameter._
import garden.Config

/**
  * Created by Basil on 09/08/2016.
  */
object PerfApp extends App {

  println("Garden of Life - Performance Checker")

  val x, y, z = 20

  val init = Config.autoBasicFlat(x, y, z, List())

  // Garden(curConf, xDim, yDim, zDim).next

  val basicTime = measure {
    Garden(init, x, y, z).next
  }

//  val warmerTime = withWarmer(new Warmer.Default) measure {
//    Garden(init, x, y, z).next
//  }

//  val configuredWarmerTime = config(
//    Key.exec.minWarmupRuns -> 20,
//    Key.exec.maxWarmupRuns -> 60,
//    Key.verbose -> true
//  ) withWarmer(new Warmer.Default) measure {
//    Garden(init, x, y, z).next
//  }

  println("Time taken: " + basicTime)
  //println("Time taken with Warmer: " + warmerTime)
  //println("Time taken with Warmer: " + configuredWarmerTime)

  val basicTimePar = measure {
    ParGarden(init, x, y, z).next
  }

//  val warmerTimePar = withWarmer(new Warmer.Default) measure {
//    ParGarden(init, x, y, z).next
//  }

//  val configuredWarmerTimePar = config(
//    Key.exec.minWarmupRuns -> 20,
//    Key.exec.maxWarmupRuns -> 60,
//    Key.verbose -> true
//  ) withWarmer(new Warmer.Default) measure {
//    ParGarden(init, x, y, z).next
//  }

  println("Par Time taken: " + basicTimePar)
  //println("Par Time taken with Warmer: " + warmerTimePar)
  //println("Par Time taken with Warmer: " + configuredWarmerTimePar)

}
