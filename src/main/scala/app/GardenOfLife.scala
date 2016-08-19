package app

import automaton.{Basic, Garden, ParGarden, Randomizer}
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
  private final val cm = new ContentModel(APP_WIDTH, APP_HEIGHT - TOOLBAR_HEIGHT)

  // garden parameters
  private final val xDim: Int = X_DIM
  private final val yDim: Int = Y_DIM
  private final val zDim: Int = Z_DIM
  private final val all = xDim * yDim * zDim
  private var curConf: List[State] = List.empty

  /***** INITIALISATION *****/

  // initialisation
  def initGarden(): List[State] = {
    //Config.basic333                                   // basic 3 * 3 * 3 garden, manual
    //Config.autoBasicFlat(xDim, yDim, zDim, List(150))    // auto garden, flat
    //Config.classic(xDim, yDim, zDim)  // classical 3D
    //Config.allOn(xDim, yDim, zDim)    // all on!
    Config.autoBasicRandom(xDim, yDim, zDim)
  }

  // application stage
  stage = new PrimaryStage {

    // toolbar
    layout.top = new ToolBar {
      content = List(
        new Button {
          text = "Home"
          minWidth = 75
          onAction = handle {step()}
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
    scene = new Scene(layout, APP_WIDTH, APP_HEIGHT, depthBuffer = true, antiAliasing = SceneAntialiasing.Balanced) {
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

    //println(System.nanoTime())
    val nextConf = Garden(curConf, xDim, yDim, zDim).next
    //val nextConf = ParGarden(curConf, xDim, yDim, zDim).next
    //val nextConf = Randomizer(curConf).next
    //val nextConf = Basic(curConf, xDim, yDim, zDim, true, 200).next
    //println(System.nanoTime())

    curConf = nextConf
    val c = cube(xDim, yDim, zDim, nextConf).flatten
    val g = new Group
    c.foreach(r => g.children.add(r))
    cm.setContent(g)

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
