package actor

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.RoundRobinPool

import scala.collection.mutable
import scala.concurrent.duration.Duration
/**
  * Created by Basil on 18/08/2016.
  *
  * http://blog.scottlogic.com/2014/08/15/using-akka-and-scala-to-render-a-mandelbrot-set.html
  * http://doc.akka.io/docs/akka/snapshot/scala/futures.html
  *
  */
class MainApp extends App {

  sealed trait AutomatonMessage
  case object Calculate extends AutomatonMessage
  case class Work(rank: Int, file: Int) extends AutomatonMessage
  case class Result(states: List[Int]) extends AutomatonMessage
  case class Final(rows: mutable.Map[Int, List[Int]], time: Duration) extends AutomatonMessage


  val system = ActorSystem("AutomatonSystem")
  val resultHandler = system.actorOf(Props[Handler], name = "resultHandler")


  class Coordinator(numWorkers: Int) extends Actor {

    var rows: mutable.Map[Int, List[Int]] = mutable.Map()
    var results = 0
    val start: Long = System.currentTimeMillis()
    val workerRouter = context.actorOf(Props[Worker].withRouter(RoundRobinPool(numWorkers)), name = "workerRouter")

    override def receive: Receive = {
      case Calculate => {
        /// divide cube into rows
        for {
          x <- (1 to 2)
          y <- (1 to 2)
        }
        workerRouter ! Work(x,y)
      }
      case Result(ss) => {
        rows ++=
        results += 1

        // complete
        if (results == numRows) {
          val t = (System.currentTimeMillis() - start).millis
          resultHandler ! Final(rows, t)
          context.stop(self)
        }
      }
    }
  }

  class Worker extends Actor {
    override def receive: Receive = {
      case Work(r, f) => sender ! Results
    }
  }

  class Handler extends Actor {
    override def receive: Receive = {
      case Final(rs, t) => {


        context.system.terminate()

      }
    }
  }


}
