package application

import application.viewer.ContentModel
import newauto.{NCell, NConfig, OneDim}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Group, Scene, SceneAntialiasing}
import scalafx.scene.control.{Button, ToolBar}
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color

object NAuto extends JFXApp {

  /***** PARAMETERS *****/

  // application parameters
  private final val layout = new BorderPane()
  private final val cubes = new Group()
  private final val cm = new ContentModel(600, 600)

  // automaton.garden parameters
  private final val xDim: Int = 11
  private final val all = xDim
  private var curConf: List[NCell] = List.empty

  /***** INITIALISATION *****/

  // initialisation
  def initGarden(): List[NCell] = NConfig.classical(xDim)

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
    val c = rowN(xDim, curConf.map(c => c.currentState))
    val g = new Group
    c.foreach(r => g.children.add(r))
    cm.setContent(g, 0, 0, 0)
    layout.center = cm.getSubScene()

    // application scene
    scene = new Scene(layout, 600, 600, depthBuffer = true, antiAliasing = SceneAntialiasing.Balanced) {
      fill = Color.Gray
      title = "Scala Garden"
    }

  }

  /***** PRCCESSING *****/

  /**
    * Process cellular automaton and update with new configuration
    */
  def step() = {

    cm.clearContent

    // Randomizer(curConf).next
    // Garden(curConf, xDim, yDim, zDim, true, 200).next

    curConf = OneDim(curConf)(xDim).next
    val c = rowN(xDim, curConf.map(c => c.currentState))
    val g = new Group
    c.foreach(r => g.children.add(r))
    cm.setContent(g, 0, 0, 0)

  }

}
