package application

import application.viewer.ContentModel
import automaton.automata.OneDimensional
import automaton.garden.{BinaryState, Config}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.{Group, Scene, SceneAntialiasing}
import scalafx.scene.control.{Button, ChoiceBox, ToolBar}
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color

/**
  * Created by Basil on 29/08/2016.
  */
object Classical1D extends JFXApp {app =>

  // application parameters
  private final val layout = new BorderPane()
  private final val cubes = new Group()
  private final val cm = new ContentModel(APP_WIDTH, APP_HEIGHT - TOOLBAR_HEIGHT)

  // automaton.garden parameters
  private final val xDim: Int = 11
  private var yDim: Int = 1
  private var all = xDim * yDim
  private var curConf: List[BinaryState] = List.empty
  private var histConf: List[BinaryState] = List.empty
  private var hist: Int = 0

  /***** INITIALISATION *****/

  // initialisation
  def initGarden(): List[BinaryState] = {

    Config.oneDimensional(xDim)

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
        }, new ChoiceBox[String]{
          maxWidth = 80
          maxHeight = 20
          items = ObservableBuffer("1D", "2D", "Garden")
          selectionModel().selectFirst()
          selectionModel().selectedItem.onChange(
            (_, _, newValue) => automatonChoice(newValue)
          )
        }
      )
    }

    // construct automaton.garden
    curConf = initGarden()
    histConf = curConf
    //val c = row(xDim, curConf)
    val c = plane(yDim,xDim, curConf)
    val g = new Group
    c.foreach(r => g.children.add(r))
    cm.setContent(g, 0, 0, 0)
    layout.center = cm.getSubScene()

    // application scene
    scene = new Scene(layout, APP_WIDTH, APP_HEIGHT, depthBuffer = true, antiAliasing = SceneAntialiasing.Balanced) {
      fill = Color.Gray
      title = "Scala Garden"
    }

  }


  def automatonChoice(ca: String) = {
    println(ca + " chosen in ChoiceBox")
  }


  def step() = {

    cm.clearContent

    yDim += 1

    curConf = OneDimensional(curConf, xDim).next

    val tmp = curConf ::: histConf
    histConf = tmp

    val c = plane(yDim, xDim, histConf)
    val g = new Group
    c.foreach(r => g.children.add(r))
    cm.setContent(g, 0, 0, 0)

  }

//    if (hist == 0) {
//      curConf = OneDimensional(curConf, xDim).next
//      val c = row(xDim, curConf)
//      val g = new Group
//      c.foreach(r => g.children.add(r))
//      cm.setContent(g)
//      hist += 1
//      histConf ++= curConf
//    } else {
//      curConf = OneDimensional(curConf, xDim).next
//      histConf ++= curConf
//      hist += 1
//      yDim += 1
//      val c = plane(xDim, yDim, histConf)
//      val g = new Group
//      c.foreach(r => g.children.add(r))
//      cm.setContent(g)
//    }
//
//  }

}


