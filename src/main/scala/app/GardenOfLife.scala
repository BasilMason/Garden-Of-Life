package app

import automaton.Automaton3d
import garden.{Config, State}
import viewer._

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.control.{Button, ToolBar}
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.scene.paint.Color
import scalafx.scene.{Group, Node, Scene, SceneAntialiasing}

/**
  * G A R D E N - O F - L I F E
  * 
  * A ScalaFX application
  */
object GardenOfLife extends JFXApp {app =>

  /***** PARAMETERS *****/

  // application parameters
  private final val layout = new BorderPane()
  private final val cubes = new Group()
  private final val cm = new ContentModel(App.APP_WIDTH, App.APP_HEIGHT - App.TOOLBAR_HEIGHT)

  // garden parameters
  private final val xDim: Int = App.X_DIM
  private final val yDim: Int = App.Y_DIM
  private final val zDim: Int = App.Z_DIM
  private final val all = xDim * yDim * zDim
  private var curConf: List[State] = List.empty

  /***** INITIALISATION *****/

  // initialisation
  def initGarden(): List[State] = {
    Config.cells
  }

  // application stage
  stage = new PrimaryStage {

    // toolbar
    layout.top = new ToolBar {
      content = List(
        new Button {
          text = "Home"
          minWidth = 75
        }, new Button {
          text = "Options"
          minWidth = 75
        }, new Button {
          text = "Help"
          minWidth = 75
        }
      )
    }

    // construct garden
    curConf = initGarden()
    val c = cube(xDim, yDim, zDim, curConf).flatten
    val g = new Group
    c.foreach(r => g.children.add(r))
    cm.setContent(g)
    layout.center = cm.getSubScene()

    // application scene
    scene = new Scene(layout, App.APP_WIDTH, App.APP_HEIGHT, depthBuffer = true, antiAliasing = SceneAntialiasing.Balanced) {
      fill = Color.Gray
      title = "Scala Garden"
    }

    // event handlers
    handleKeyboard(scene(), layout)

  }

  /***** PRCCESSING *****/

  /**
    * Process cellular automaton and update with new configuration
    */
  def step() = {

    cm.clearContent
    val nextConf = Automaton3d(curConf, xDim, yDim, zDim, c => c).next
    curConf = nextConf
    val c = cube(xDim, yDim, zDim, nextConf).flatten
    val g = new Group
    c.foreach(r => g.children.add(r))
    cm.setContent(g)

  }

  /***** CONSTRUCTION *****/

  /**
    * Given a list of states, construct a 3D cube of cells in those given states
    * @param x - size in x-dimension
    * @param y - size in y-dimension
    * @param z - size in z-dimension
    * @param conf - list of cell states
    * @return - List of lists of ScalaFX HBoxes containing all cells in 3D grid pattern
    */
  def cube(x: Int, y: Int, z: Int, conf: List[State]): List[List[HBox]] = {

    /**
      * Internal method - constructs a plane in the cube
      * @param x - size in x-dimension
      * @param y - size in x-dimension
      * @param conf - list of cell states
      * @return - List of ScalaFX HBoxes containing a 2D plane of cells
      */
    def plane(x: Int, y: Int, conf: List[State]): List[HBox] = {

      val acc = List()
      val z = 0
      val subConfs = conf.grouped(y).toList

      def h(acc: List[HBox], length: Int, z: Int, conf: List[List[State]]): List[HBox] = length match {
        case 0 => acc
        case _ => {
          val r = row(y, conf.head)
          r.map(c => c.translateX = 0)
          val hb = new HBox {
            translateZ = z
            spacing = 5
            padding = Insets(5)
          }
          r.foreach(c => hb.children.add(c: Cell))
          List(hb) ::: h(acc, length - 1, z + 15, conf.tail)
        }
      }

      h(acc, x, z, subConfs)

    }

    /**
      * Internal method - constructs a single row of the cube
      * @param x - size in x-dimension
      * @param conf - list of cell states
      * @return - a 1D list of cells
      */
    def row(x: Int, conf: List[State]): List[Cell] = {

      val acc = List()
      val z = 0

      def h(acc: List[Cell], length: Int, z: Int, conf: List[State]): List[Cell] = length match {

        case 0 => acc
        case _ => {
          val s = conf.head
          val c = Cell(s.s)
          c.translateX = z
          List(c) ::: h(acc, length - 1, z + 15, conf.tail)}
      }

      h(acc, x, z, conf)

    }

    // initialise cube
    val acc = List()
    val d = 0
    val subConfs = conf.grouped(x * y).toList

    // constructor
    def h(acc: List[List[HBox]], length: Int, d: Int, confs: List[List[State]]): List[List[HBox]] = length match {
      case 0 => acc
      case _ => {
        val p = plane(x, y, confs.head)
        p.map(c => c.translateY = d)
        List(p) ::: h(acc, length - 1, d + 15, confs.tail)
      }
    }

    // return
    h(acc, z, d, subConfs)

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
          step()
        }

        case _ =>
      }
    }
  }
}
