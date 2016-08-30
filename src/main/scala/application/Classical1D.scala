package application

import application.viewer.ContentModel
import automaton.automata.OneDimensional
import automaton.garden.{BinaryState, Config}
import javafx.scene.control.{ToggleButton => JfxTog}
import javafx.scene.control.{TextField => JfxTf}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.{Group, Scene, SceneAntialiasing}
import scalafx.scene.control._
import scalafx.scene.effect.InnerShadow
import scalafx.scene.layout.{BorderPane, HBox, Priority, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Arc
import scalafx.scene.text.Font

/**
  * Created by Basil on 29/08/2016.
  */
object Classical1D extends JFXApp {app =>

  // application parameters
  private final val layout = new BorderPane()
  private final val cubes = new Group()
  private final val cm = new ContentModel(APP_WIDTH - 150, APP_HEIGHT - TOOLBAR_HEIGHT)

  // automaton.garden parameters
  private final val xDim: Int = 101
  private var yDim: Int = 1
  private var all = xDim * yDim
  private var curConf: List[BinaryState] = List.empty
  private var histConf: List[BinaryState] = List.empty
  private var hist: Int = 0
  private var typeSelection = "1D"
  private var xSays = ""

  // application GUI controls
  val xInput = new TextField {
    text = "3"
    maxWidth = 150
  }
  /***** INITIALISATION *****/

  // initialisation
  def initGarden(): List[BinaryState] = {

    Config.oneDimensional(xDim)

  }

  // application stage
  stage = new PrimaryStage {

    layout.right = new Accordion {

      minWidth = 150.0

      panes = List(
        new TitledPane() {
          text = "Type"
          content = new Button("One")
        }
      )

      expandedPane = panes.head

    }

    val tog = new ToggleGroup {
      selectedToggle.onChange(
        (_, oldValue, newValue) => automatonChoice(newValue.asInstanceOf[JfxTog].getText)
      )
    }

    val xInput = new TextField {
      promptText = "3"
      maxWidth = 150

    }
    xInput.onAction = (e: ActionEvent) => {
      xSays = xInput.text()
    }

    layout.left = new Accordion {
      minWidth = 300.0
      maxHeight = 300.0
      panes = List(
        new TitledPane {
          text = "Type"
          collapsible = false
          prefHeight = 200
          content = new VBox {
            padding = Insets(20)
            spacing = 10
            alignment = Pos.TopLeft
            children = Seq(
              new RadioButton {
                maxWidth = 100
                maxHeight = 50
                text = "1D"
                toggleGroup = tog
                selected = true
              },
              new RadioButton {
                maxWidth = 100
                maxHeight = 50
                text = "2D"
                toggleGroup = tog
              },
              new RadioButton {
                maxWidth = 100
                maxHeight = 50
                text = "3D"
                toggleGroup = tog
              },
              new HBox {
                spacing = 10
                padding = Insets(20)
                children = Seq(
                  new Label {
                    text = "X:"
                  },
                  xInput
                )
              },
              new HBox {
                spacing = 10
                padding = Insets(20)
                children = Seq(
                  new Label {
                    text = "Y:"
                  },
                  new TextField {
                    promptText = "3"
                    maxWidth = 150
                  }
                )
              }
            )
          }

        },
        new TitledPane {
          text = "Behavior"
          content = new Button("Two")

        }
        ,
        new TitledPane {
          text = "Settings"
          content = new Button("Three")

        }
      )
      expandedPane = panes.head
    }

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
          onAction = handle {beginAutomaton()}
        }, new Button {
          text = "Help"
          minWidth = 75
          onAction = handle {testGui()}
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

    layout.center = cm.getSubScene()

    displayIntro()

    // application scene
    scene = new Scene(layout, APP_WIDTH, APP_HEIGHT, depthBuffer = true, antiAliasing = SceneAntialiasing.Balanced) {
      fill = Color.Gray
      title = "Scala Garden"
    }

  }

  def displayIntro() = {

    val display = new VBox {
      vgrow = Priority.Always
      hgrow = Priority.Always
      spacing = 10
      padding = Insets(20)
      children = List(
        new Label {
          text = "Welcome to CA"
          font = new Font("Arial Black", 30)
          textFill = Color.web("#BBBBBB")
          effect = new InnerShadow {
            offsetX = 3
            offsetY = 3
            radius = 5d
          }
        },
        new Label {
          text = "Select an option"
          font = new Font("Arial Black", 30)
          textFill = Color.web("#BBBBBB")
          effect = new InnerShadow()
        }
      )
    }

    val g = new Group
    g.children.add(display)
    cm.setContent(g,0,0,0)

  }

  def testGui() = {
    println("X: " + xInput.text())
    println("XS: " + xSays)
  }

  def beginAutomaton() = {
    // construct automaton.garden
    curConf = initGarden()
    histConf = curConf
    //val c = row(xDim, curConf)
    val c = plane(yDim,xDim, curConf)
    val g = new Group
    c.foreach(r => g.children.add(r))
    cm.setContent(g, ((xDim * 15)+5)/2, 0, 0)
  }

  def automatonChoice(ca: String) = {
    println(ca + " chosen in ChoiceBox")
    println("X says:" + xInput.text())
    typeSelection = ca
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
    cm.setContent(g, ((xDim * 15)+5)/2, 0, 0)

  }

}


