package performance

import automaton.{Basic, Garden, ParGarden}
import org.scalameter._
import garden.Config

/**
  * Created by Basil on 09/08/2016.
  */
object PerfApp extends App {

//  println("Map-Vals - Performance Checker")
//
//  val am = (1 to 1000 by 2).toList
//  val bm = (2 to 1001 by 2).toList
//  val cm = am zip bm
//  val mm = cm.toMap
//
//  val time1 = withWarmer(new Warmer.Default) measure {
//    mapToMap[Int, Int, Int](mm, x => x * x)
//  }
//
//  val time2 = withWarmer(new Warmer.Default) measure {
//    mapToMap2[Int, Int, Int](mm, x => x * x)
//  }
//
//  val time3 = withWarmer(new Warmer.Default) measure {
//    mapToMap3[Int, Int, Int](mm, x => x * x)
//  }
//
//  println("Map-Vals xs.toSet: " + time1)
//  println("Map-Vals ks.tail: " + time2)
//  println("Map-Vals ks - x: " + time3)
//
//  def mapToMap[A,B,C](in: Map[A, B], op: B => C): Map[A, C] = {
//    def h(acc: Map[A, C], ks: Set[A]): Map[A, C] = ks.toList match {
//      case Nil => acc
//      case x :: xs => {
//        val v = in(x)
//        val e = op(v)
//        h(acc + (x -> e), xs.toSet)
//      }
//    }
//    h(Map.empty[A, C], in.keySet)
//  }
//
//  def mapToMap2[A,B,C](in: Map[A, B], op: B => C): Map[A, C] = {
//    def h(acc: Map[A, C], ks: Set[A]): Map[A, C] = ks.toList match {
//      case Nil => acc
//      case x :: xs => {
//        val v = in(x)
//        val e = op(v)
//        h(acc + (x -> e), ks.tail)
//      }
//    }
//    h(Map.empty[A, C], in.keySet)
//  }
//
//  def mapToMap3[A,B,C](in: Map[A, B], op: B => C): Map[A, C] = {
//    def h(acc: Map[A, C], ks: Set[A]): Map[A, C] = ks.toList match {
//      case Nil => acc
//      case x :: xs => {
//        val v = in(x)
//        val e = op(v)
//        h(acc + (x -> e), ks - x)
//      }
//    }
//    h(Map.empty[A, C], in.keySet)
//  }

  println("Garden of Life - Performance Checker")

  val x, y, z = 20

  val init = Config.classic(x, y, z)

  val basicTime = measure {
    Basic(init, x, y, z, false, 0).next
  }

  val warmerTime = withWarmer(new Warmer.Default) measure {
    Basic(init, x, y, z, false, 0).next
  }

  println("Time taken: " + basicTime)
  println("Time taken with Warmer: " + warmerTime)

  val basicTimePar = measure {
    Basic(init, x, y, z, true, 200).next
  }

  val warmerTimePar = withWarmer(new Warmer.Default) measure {
    Basic(init, x, y, z, true, 200).next
  }

  println("Par Time taken: " + basicTimePar)
  println("Par Time taken with Warmer: " + warmerTimePar)





  //  val warmerTime = withWarmer(new Warmer.Default) measure {
  //    Garden(init, x, y, z).next
  //  }
  //println("Time taken with Warmer: " + configuredWarmerTime)
  //  val configuredWarmerTime = config(
  //    Key.exec.minWarmupRuns -> 20,
  //    Key.exec.maxWarmupRuns -> 60,
  //    Key.verbose -> true
  //  ) withWarmer(new Warmer.Default) measure {
  //    Garden(init, x, y, z).next
  //  }


//  val configuredWarmerTimePar = config(
//    Key.exec.minWarmupRuns -> 20,
//    Key.exec.maxWarmupRuns -> 60,
//    Key.verbose -> true
//  ) withWarmer(new Warmer.Default) measure {
//    ParGarden(init, x, y, z).next
//  }


  //println("Par Time taken with Warmer: " + configuredWarmerTimePar)

}
