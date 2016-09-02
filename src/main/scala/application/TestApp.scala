package application


import javafx.scene.control.{ToggleButton => JfxToggleBtn}

import application.viewer.ContentModel
import automaton.automata.{Garden, OneDimensional}
import automaton.garden.{BinaryState, Config, GardenState}
import newauto.{GardenPar, NCell, NConfig}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.{Group, Scene}
import scalafx.scene.control._
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout._

/**
  * Created by Basil on 07/08/2016.
  */
object TestApp extends JFXApp {

  private final val layout = new BorderPane()
  private final val cubes = new Group()
  private final val cm = new ContentModel(1200 - 244, 800 - 70)
  private var curConfN: List[NCell] = List.empty
  private var curConfBin: List[BinaryState] = List.empty
  private var histConfBin: List[BinaryState] = List.empty
  private var curConfGar: List[GardenState] = List.empty
  private var histConfGar: List[GardenState] = List.empty
  private var dimensionsChoice = "1D"
  private var perfChoice = "None"
  private var typeChoice = "Classical"
  private var xDim = 10
  private var yDim = 10
  private var zDim = 10
  private var histCount = 1
  private var running = false

  // dimensions

  val dimensionLabel = new Label {
    text = "Dimensions: ?"
    style = "-fx-font-size: 1em;"
  }

  val dimensionToggle = new ToggleGroup {
    selectedToggle.onChange(
      (_, oldValue, newValue) => dimensionChoiceHandler(newValue.asInstanceOf[JfxToggleBtn].getText)
    )
  }

  val dimensionSelector = new VBox {
    vgrow = Priority.Always
    hgrow = Priority.Always
    spacing = 10
    padding = Insets(20)
    children = List(
      new HBox {
        spacing = 6
        children = List(
          new ToggleButton {
            minWidth = 50
            text = "1D"
            toggleGroup = dimensionToggle
          },
          new ToggleButton {
            minWidth = 50
            text = "2D"
            toggleGroup = dimensionToggle
          },
          new ToggleButton {
            minWidth = 50
            text = "3D"
            toggleGroup = dimensionToggle
          })
      },
      dimensionLabel
    )
  }

  // size

  val xLabel = new Label("X:") {
    style = "-fx-font-weight:bold"
    alignmentInParent = Pos.BaselineRight
  }
  GridPane.setConstraints(xLabel, 0, 0, 1, 1)

  val xInput = new TextField {
    promptText = "Enter X Dimension here..."
  }
  xInput.text.onChange {
    xDim = xInput.text().toInt
  }
  GridPane.setConstraints(xInput, 1, 0, 2, 1)

  val yLabel = new Label("Y:") {
    style = "-fx-font-weight:bold"
    alignmentInParent = Pos.BaselineRight
  }
  GridPane.setConstraints(yLabel, 0, 1, 1, 1)

  val yInput = new TextField {
    promptText = "Enter Y Dimension here..."
  }
  yInput.text.onChange {
    yDim = yInput.text().toInt
  }
  GridPane.setConstraints(yInput, 1, 1, 2, 1)

  val zLabel = new Label("Z:") {
    style = "-fx-font-weight:bold"
    alignmentInParent = Pos.BaselineRight
  }
  GridPane.setConstraints(zLabel, 0, 2, 1, 1)

  val zInput = new TextField {
    promptText = "Enter Z Dimension here..."
  }
  zInput.text.onChange {
    zDim = zInput.text().toInt
  }
  GridPane.setConstraints(zInput, 1, 2, 2, 1)

  val sizeGrid = new GridPane {
    hgap = 4
    vgap = 6
    margin = Insets(18)
    children = Seq(xLabel, xInput, yLabel, yInput, zLabel,zInput)
  }

  // type

  val typeToggle = new ToggleGroup {
    selectedToggle.onChange(
      (_, oldValue, newValue) => typeChoiceHandler(newValue.asInstanceOf[JfxToggleBtn].getText)
    )
  }

  val ts = new VBox {
    vgrow = Priority.Always
    hgrow = Priority.Always
    spacing = 10
    padding = Insets(20)
    children = List(
      new RadioButton {
        maxWidth = 100
        maxHeight = 50
        text = "Classical"
        toggleGroup = typeToggle
        selected = true
      },
      new RadioButton {
        maxWidth = 100
        maxHeight = 50
        text = "Game Of Life"
        toggleGroup = typeToggle
      },
      new RadioButton {
        maxWidth = 100
        maxHeight = 50
        text = "Garden"
        toggleGroup = typeToggle
      }
    )
  }

  // decoration - read only text area

  val iconView = new ImageView {
    image = new Image(this.getClass.getResourceAsStream("/GoL-icon.png"),100, 100, false, false)
    alignmentInParent = Pos.Center
  }

  // left

  val leftPane = new VBox {
    maxWidth = 244
    minWidth = 244
    vgrow = Priority.Always
    hgrow = Priority.Always
    spacing = 10
    padding = Insets(20)
    alignmentInParent = Pos.Center
    children = List(
      dimensionSelector,
      new Separator(),
      sizeGrid,
      new Separator(),
      ts,
      new Separator(),
      iconView
    )
  }

  // right

  // perf selector

  val perfLabel = new Label {
    text = "Performance: ?"
    style = "-fx-font-size: 1em;"
  }

  val perfToggle = new ToggleGroup {
    selectedToggle.onChange(
      (_, oldValue, newValue) => perfChoiceHandler(newValue.asInstanceOf[JfxToggleBtn].getText)
    )
  }

  val perfSelector = new VBox {
    vgrow = Priority.Always
    hgrow = Priority.Always
    spacing = 10
    padding = Insets(20)
    children = List(
      new HBox {
        spacing = 6
        children = List(
          new ToggleButton {
            minWidth = 50
            text = "None"
            toggleGroup = perfToggle
          },
          new ToggleButton {
            minWidth = 50
            text = "Parallel"
            toggleGroup = perfToggle
          },
          new ToggleButton {
            minWidth = 50
            text = "AKKA"
            toggleGroup = perfToggle
          })
      },
      perfLabel
    )
  }

  val logArea = new TextArea {
    editable = false
    promptText = "Logging..."
    minHeight = 500
  }

  val rightPane = new VBox {
    maxWidth = 244
    minWidth = 244
    vgrow = Priority.Always
    hgrow = Priority.Always
    spacing = 10
    padding = Insets(20)
    alignmentInParent = Pos.Center
    children = List(
      perfSelector,
      new Separator(),
      logArea
    )
  }

  // top

  val topPane = new ToolBar {
    minHeight = 70
    maxHeight = 70
    content = List(
      new Button {
        text = "Start"
        minWidth = 75
        onAction = handle {startAutomaton()}
      }, new Button {
        text = "Play"
        minWidth = 75
        onAction = handle {playAutomaton()}
      }, new Button {
        text = "Stop"
        minWidth = 75
        onAction = handle {stopAutomaton()}
      }
    )
  }

  stage = new PrimaryStage {
    title = "Test"
    scene = new Scene(1444,800) {

      layout.top = topPane
      layout.left = leftPane
      layout.right = rightPane
      layout.center = cm.getSubScene()
      content = List(layout)

    }
  }

  // handler

  def dimensionChoiceHandler(ca: String) = {

    dimensionLabel.text = "Dimensions: " + ca
    dimensionsChoice = ca

  }

  def typeChoiceHandler(t: String) = {

    typeChoice = t

  }

  def perfChoiceHandler(ca: String) = {

    perfLabel.text = "Performance: " + ca
    perfChoice = ca

  }


  def startAutomaton() = {

    println("Starting Automaton")
    println("Dimensions:" + dimensionsChoice)
    println("Type: " + typeChoice)
    println("X: " + xDim)
    println("Y: " + yDim)
    println("Z: " + zDim)

    histCount = 1
    curConfBin = List.empty
    histConfBin = List.empty

    dimensionsChoice match {
      case "1D" => {

        cm.clearContent

        curConfBin = Config.oneDimensional(xDim)
        histConfBin = curConfBin
        val c = plane(histCount,xDim, curConfBin)
        val g = new Group
        c.foreach(r => g.children.add(r))
        cm.setContent(g, ((xDim * 15)+5)/2, 0, 0)
      }
      case "2D" => {

        cm.clearContent

        curConfN = NConfig.rugged(xDim, yDim, zDim)
        val c = cubeN(xDim, yDim, zDim, curConfN.map(c => c.currentState)).flatten
        val g = new Group
        c.foreach(r => g.children.add(r))
        cm.setContent(g, ((xDim * 15)+5)/2, 0, 0)
        layout.center = cm.getSubScene()

      }
      case "3D" => {

        cm.clearContent

        curConfGar = Config.autoBasicRandom(xDim, yDim, zDim)
        val c = cube(xDim, yDim, zDim, curConfGar).flatten
        val g = new Group
        c.foreach(r => g.children.add(r))
        cm.setContent(g, ((xDim * 15)+5)/2, 0, 0)
        layout.center = cm.getSubScene()

      }
    }

  }

  def playAutomaton() = {

    println("Playing Automaton")

    dimensionsChoice match {
      case "1D" => {

        cm.clearContent

        histCount += 1

        curConfBin = OneDimensional(curConfBin, xDim).next

        val tmp = curConfBin ::: histConfBin
        histConfBin = tmp

        val c = plane(histCount, xDim, histConfBin)
        val g = new Group
        c.foreach(r => g.children.add(r))
        cm.setContent(g, ((xDim * 15)+5)/2, 0, 0)

      }
      case "2D" => {

        cm.clearContent

        curConfN = GardenPar(curConfN)(xDim, yDim, zDim)(3).next
        val c = cubeN(xDim, yDim, zDim, curConfN.map(c => c.currentState)).flatten
        val g = new Group
        c.foreach(r => g.children.add(r))
        cm.setContent(g, ((xDim * 15)+5)/2, 0, 0)

      }
      case "3D" => {

        cm.clearContent

        curConfGar = Garden(curConfGar, xDim, yDim, zDim, true, 200).next
        val c = cube(xDim, yDim, zDim, curConfGar).flatten
        val g = new Group
        c.foreach(r => g.children.add(r))
        cm.setContent(g, ((xDim * 15)+5)/2, 0, 0)


      }
    }

  }

  def stopAutomaton() = {

    println("Stoping Automaton")

  }

}


//cm.clearContent
//
//yDim += 1
//
//curConf = OneDimensional(curConf, xDim).next
//
//val tmp = curConf ::: histConf
//histConf = tmp
//
//val c = plane(yDim, xDim, histConf)
//val g = new Group
//c.foreach(r => g.children.add(r))
//cm.setContent(g, ((xDim * 15)+5)/2, 0, 0)


//      val label = new Label("Test Label")
//      label.layoutX = 20
//      label.layoutY = 20
//
//      val textField1 = new TextField
//      textField1.layoutX = 20
//      textField1.layoutY = 50
//      textField1.promptText = "Enter X Dimension here..."
//
//      val textField2 = new TextField
//      textField2.layoutX = 20
//      textField2.layoutY = 80
//      textField2.promptText = "Enter Y Dimension here..."

//content = List(label, textField1, textField2)

//      textField1.onAction = (e: ActionEvent) => {
//        label.text = "Typed: " + textField1.text()
//      }
//
//      textField2.text.onChange {
//        label.text = "Change: " + textField2.text()
//      }
