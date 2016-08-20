package application

import automaton.automata.{Garden, Randomizer}
import automaton.garden.{Config, State}
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

  // automaton.garden parameters
  private final val xDim: Int = X_DIM
  private final val yDim: Int = Y_DIM
  private final val zDim: Int = Z_DIM
  private final val all = xDim * yDim * zDim
  private var curConf: List[State] = List.empty

  /***** INITIALISATION *****/

  // initialisation
  def initGarden(): List[State] = {

    // Config.basic333
    // Config.autoBasicFlat(xDim, yDim, zDim, List(13))
    // Config.classic(xDim, yDim, zDim)
    // Config.allOn(xDim, yDim, zDim)
    // Config.autoBasicRandom(xDim, yDim, zDim)

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

    // construct automaton.garden
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

    // Randomizer(curConf).next
    // Garden(curConf, xDim, yDim, zDim, true, 200).next

    curConf = Garden(curConf, xDim, yDim, zDim, true, 200).next
    val c = cube(xDim, yDim, zDim, curConf).flatten
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
