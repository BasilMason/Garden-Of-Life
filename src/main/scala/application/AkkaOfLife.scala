package application

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.actor.Actor.Receive
import akka.routing.RoundRobinPool
import automaton.Vect
import automaton.automata.Garden
import automaton.garden.{Config, PadState, RedState, State}
import viewer.ContentModel

import scala.collection.mutable
import scalafx.Includes._
import scalafx.application.{JFXApp, Platform}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Group, Node, Scene, SceneAntialiasing}
import scalafx.scene.control.{Button, ToolBar}
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color
import scalafx.util.Duration

/**
  * Created by Basil on 18/08/2016.
  * http://blog.scottlogic.com/2014/08/15/using-akka-and-scala-to-render-a-mandelbrot-set.html
  */
object AkkaOfLife extends JFXApp {app =>

  /***** PARAMETERS *****/

  // application parameters
  private final val layout = new BorderPane()
  private final val cubes = new Group()
  private final val cm = new ContentModel(APP_WIDTH, APP_HEIGHT - TOOLBAR_HEIGHT)

  // automaton.garden parameters
  private final val xDim: Int = X_DIM
  private final val yDim: Int = Y_DIM
  private final val zDim: Int = Z_DIM
  private final val all = xDim * yDim * zDim
  private var curConf: List[State] = List.empty

  // initialisation
  def initGarden(): List[State] = Config.classic(xDim, yDim, zDim)

  // application stage
  stage = new PrimaryStage {

    // toolbar
    layout.top = new ToolBar {
      content = List(
        new Button {
          text = "Home"
          minWidth = 75
          onAction = handle {stepHandler()}
        }, new Button {
          text = "Options"
          minWidth = 75
        }, new Button {
          text = "Help"
          minWidth = 75
        }
      )
    }

    // construct automaton.garden
    curConf = initGarden()
    val c = cube(xDim, yDim, zDim, curConf).flatten
    val g = new Group
    c.foreach(r => g.children.add(r))
    cm.setContent(g, 0, 0, 0)
    layout.center = cm.getSubScene()

    // application scene
    scene = new Scene(layout, APP_WIDTH, APP_HEIGHT, depthBuffer = true, antiAliasing = SceneAntialiasing.Balanced) {
      fill = Color.Gray
      title = "Scala Garden"
    }

    // event handlers
    handleKeyboard(scene(), layout)

  }

  stage.onCloseRequest = () => {
    system.terminate()
  }

  // AKKA
  val system = ActorSystem("AutomatonSystem")
  val resultHandler = system.actorOf(Props[Handler], name = "resultHandler")


  def stepHandler() = {

    println("Handling Step")

    val workers = 4
    val coordinator = system.actorOf(Props(new Coordinator(workers, resultHandler)), name = "coordinator")

    println("Sending")
    coordinator ! Calculate
  }

  def step(next: List[State]) = {

    println("Step")

    cm.clearContent
    curConf = next
    val c = cube(xDim, yDim, zDim, next).flatten
    val g = new Group
    c.foreach(r => g.children.add(r))
    cm.setContent(g, 0, 0, 0)

  }




  /***** AKKA HELPERS *****/

  sealed trait AutomatonMessage
  case object Calculate extends AutomatonMessage
  case class Work(plane: Int, space: Grid) extends AutomatonMessage
  case class Result(states: (Int, List[State])) extends AutomatonMessage
  case class Final(planes: mutable.Map[Int, List[State]], time: Int) extends AutomatonMessage

  class Worker extends Actor with ActorLogging {
    override def receive: Receive = {
      case Work(pl, gr) => {

        log.info(s"${self.path.name} Work received.")

        val coords = for {
          ys <- (0 until xDim)
          xs <- (0 until yDim)
        } yield (xs, ys, pl)

        val states = coords.map(c => {
          val cell = gr(c)
          val state = cell._1
          val n = neighbours(c)
          val ns = neighbourStates(n, gr)
          transition(state, ns)
        }).toList

        sender ! Result((pl, states))
        //context.stop(self)

      }
    }
  }
  class Handler extends Actor with ActorLogging {
    override def receive: Receive = {
      case Final(pls, t) => {

        log.info(s"${self.path.name} Final received.")

        val res = for (i <- (0 until zDim)) yield pls(i)

        Platform.runLater {
          step(res.toList.flatten)
        }

        //context.system.terminate()  // required?
      }
    }
  }

  class Coordinator(numWorkers: Int, resultHandler: ActorRef) extends Actor with ActorLogging {

    var rows: scala.collection.mutable.Map[Int, List[Int]] = mutable.Map()
    var results = 0
    val start: Long = System.currentTimeMillis()
    val workerRouter = context.actorOf(Props[Worker].withRouter(RoundRobinPool(numWorkers)), name = "workerRouter")
    var planes: mutable.Map[Int, List[State]] = mutable.Map()


    override def receive: Receive = {
      case Calculate => {

        log.info(s"${self.path.name} Calculate received.")

        val inputWithIndex = curConf zipWithIndex
        val grid = coordinateTable(inputWithIndex, xDim, yDim, zDim)

        for (zs <- (0 until zDim))
          workerRouter ! Work(zs, grid)

      }
      case Result((pl, ss)) => {

        log.info(s"${self.path.name} Result received.")

        planes += (pl -> ss)
        results += 1

        // finish time/duration

        if (results == zDim) {
          resultHandler ! Final(planes, 1)
          context.stop(self)  // required? On worker?
        }

      }
    }
  }

  /***** AUTOMATON HELPERS *****/
  type Global = List[State]
  type Coordinate = (Int, Int, Int)
  type Neighbours = Map[String, State]
  type Configuration = List[Cell]
  type Grid = Map[Coordinate, Cell]
  type Cell = (State, Int)

  def coordinateTable(init: Configuration, xDim: Int, yDim: Int, zDim: Int): Grid = {
    init.map(in => (indexToCoordinate(xDim, yDim, zDim)(in._2), in)).toMap
  }

  def indexToCoordinate(x: Int, y: Int, z: Int)(i: Int): Coordinate = {
    (
      if (x==0) 0
      else i % x,
      if (y==0) 0
      else (if (x==0) 0
      else (i / x) % y),
      if (z==0) 0
      else if (x==0 || y==0) 0
      else (i / (x * y)) % z
      )
  }

  def getCoordinate = indexToCoordinate(xDim, yDim, zDim)(_)

  def transition(current: State, ns: Neighbours): State = {

    val ss = ns.values.toList
    val cs = ss.map(s => s match {
      case RedState => 1
      case _ => 0
    }).sum

    cs match {
      case n if n > 0 && n < 3 => RedState
      case _ => PadState
    }

  }

  def boundary(i: Int): Int = i
  def neighbours(in: Coordinate): Map[String, Coordinate] = {

    Map(
      "LEFT" -> (boundary(in._1 - 1), in._2, in._3)
      , "RIGHT" -> (boundary(in._1 + 1), in._2, in._3)
      , "TOP" -> (in._1, boundary(in._2 - 1), in._3)
      , "BOTTOM" -> (in._1, boundary(in._2 + 1), in._3)
      , "FRONT" -> (in._1, in._2, boundary(in._3 - 1))
      , "BACK" -> (in._1, in._2, boundary(in._3 + 1))
    )

  }

  def neighbourStates(in: Map[String, Coordinate], space: Grid): Neighbours = mapToMap[String, Coordinate, State](in, c => space.getOrElse(c, (PadState, -1))._1)

  def mapToMap[A,B,C](in: Map[A, B], op: B => C): Map[A, C] = {
    def h(acc: Map[A, C], ks: Set[A]): Map[A, C] = ks.toList match {
      case Nil => acc
      case x :: xs => {
        val v = in(x)
        val e = op(v)
        h(acc + (x -> e), ks - x)
      }
    }
    h(Map.empty[A, C], in.keySet)
  }

  /***** EVENT HANDLERS *****/

  private def handleKeyboard(scene: Scene, root: Node) {
    //    val moveCamera: Boolean = true
    scene.onKeyPressed = (event: KeyEvent) => {
      //      val currentTime: Duration = null
      event.code match {
        case KeyCode.Z => {
          println("z")
          cm.clearContent
        }
        case KeyCode.X => {
          println("x")
          stepHandler()
        }

        case _ =>
      }
    }
  }

}
