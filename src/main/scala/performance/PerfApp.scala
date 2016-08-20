package performance


import org.scalameter._
import parallel.TaskManager

/**
  * Created by Basil on 09/08/2016.
  */
object PerfApp extends App {

  val a1 = (1 to 1000).toList
  val a2 = (1 to 10000).toList
  val a3 = (1 to 100000).toList
  val a4 = (1 to 1000000).toList
  val a5 = (1 to 10000000).toList

//  val time1 = withWarmer(new Warmer.Default) measure {
//    traverse(a1)
//  }
//
//  val time2 = withWarmer(new Warmer.Default) measure {
//    traverse(a2)
//  }
//
//  val time3 = withWarmer(new Warmer.Default) measure {
//    traverse(a3)
//  }
//
//  val time4 = withWarmer(new Warmer.Default) measure {
//    traverse(a4)
//  }

  val time5 = withWarmer(new Warmer.Default) measure {
    traverse(a5)
  }

//  println("1000: " + time1)
//  println("10000: " + time2)
//  println("100000: " + time3)
//  println("1000000: " + time4)
  println("10000000: " + time5)

//  val parTime1 = withWarmer(new Warmer.Default) measure {
//    reduce(a1, 100000)
//  }
//
//  val parTime2 = withWarmer(new Warmer.Default) measure {
//    reduce(a2, 100000)
//  }
//
//  val parTime3 = withWarmer(new Warmer.Default) measure {
//    reduce(a3, 100000)
//  }
//
//  val parTime4 = withWarmer(new Warmer.Default) measure {
//    reduce(a4, 100000)
//  }

  val parTime5 = withWarmer(new Warmer.Default) measure {
    reduce(a5, 1000000)
  }

//  println("1000 par: " + parTime1)
//  println("10000 par: " + parTime2)
//  println("100000 par: " + parTime3)
//  println("1000000 par: " + parTime4)
  println("10000000 par: " + parTime5)

  def traverse(in: List[Int]): List[Int] = {

    in.map(x => x * x)

  }

  def reduce(in: List[Int], t: Int): List[Int] = {

    if (in.length < t) {
      traverse(in)
    } else {

      val m = in.length / 2
      val l = in.take(m)
      val r = in.drop(m)

      val (t1, t2) = TaskManager.parallel(reduce(l, t), reduce(r, t))

      t1 ::: t2

    }
  }

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

//  println("Garden of Life - Performance Checker")
//
//  val x, y, z = 20
//
//  val init = Config.classic(x, y, z)
//
//  val basicTime = measure {
//    Basic(init, x, y, z, false, 0).next
//  }
//
//  val warmerTime = withWarmer(new Warmer.Default) measure {
//    Basic(init, x, y, z, false, 0).next
//  }
//
//  println("Time taken: " + basicTime)
//  println("Time taken with Warmer: " + warmerTime)
//
//  val basicTimePar = measure {
//    Basic(init, x, y, z, true, 200).next
//  }
//
//  val warmerTimePar = withWarmer(new Warmer.Default) measure {
//    Basic(init, x, y, z, true, 200).next
//  }
//
//  println("Par Time taken: " + basicTimePar)
//  println("Par Time taken with Warmer: " + warmerTimePar)
//
//
//


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
