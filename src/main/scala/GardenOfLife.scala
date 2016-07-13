import automaton.Automaton3d
import garden.{Config, State}

import scalafx.application.JFXApp
import viewer._
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.scene.paint.Color
import scalafx.scene.{SceneAntialiasing, Node, Scene, Group}
import scalafx.scene.control.{ToolBar, Button}
import scalafx.scene.layout.{HBox, BorderPane}

/**
  * Created by Basil on 13/07/2016.
  */
object GardenOfLife extends JFXApp {app =>

  private final val layout = new BorderPane()
  private final val cubes = new Group()
  private final val cm = new ContentModel(800, 580)
  private final val xDim: Int = 3
  private final val yDim: Int = 3
  private final val zDim: Int = 3
  private final val all = xDim * yDim * zDim
  private var curConf: List[State] = List.empty

  def initGarden(): List[State] = {
    Config.cells
  }

  // stage
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

    curConf = initGarden()

    val c = cube(xDim, yDim, zDim, curConf).flatten
    val g = new Group
    c.foreach(r => g.children.add(r))
    cm.setContent(g)
    layout.center = cm.getSubScene()

    // scene
    scene = new Scene(layout, 800, 600, depthBuffer = true, antiAliasing = SceneAntialiasing.Balanced) {
      fill = Color.Gray
      title = "Scala Garden"
    }

    handleKeyboard(scene(), layout)

  }

  def cube(x: Int, y: Int, z: Int, conf: List[State]): List[List[HBox]] = {

    val acc = List()
    val d = 0
    val subConfs = conf.grouped(x * y).toList

    def h(acc: List[List[HBox]], length: Int, d: Int, confs: List[List[State]]): List[List[HBox]] = length match {
      case 0 => acc
      case _ => {
        val p = plane(x, y, confs.head)
        p.map(c => c.translateY = d)
        List(p) ::: h(acc, length - 1, d + 15, confs.tail)
      }
    }

    h(acc, z, d, subConfs)

  }

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

  def row(length: Int, conf: List[State]): List[Cell] = {

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

    h(acc, length, z, conf)

  }

  def step() = {

    cm.clearContent
    //val nextConf = Automaton3d(curConf, xDim, yDim, zDim,c => c).next
    //curConf = nextConf
    //val c = cube(xDim, yDim, zDim, nextConf).flatten
    //val g = new Group
    //c.foreach(r => g.children.add(r))
    //cm.setContent(g)

  }

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
