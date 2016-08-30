package application.viewer

/**
  * Created by Basil on 13/07/2016.
  */
import scalafx.Includes._
import scalafx.scene._
import scalafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.scene.paint.{PhongMaterial, Color}
import scalafx.scene.shape.Box

/**
  * 3D Content Model for Viewer App.
  * Contains the 3D subscene and everything related to it: light, cameras, axis
  * Based on com.javafx.experiments.jfx3dviewer.ContentModel
  *
  * @author jpereda, April 2014 - @JPeredaDnr
  */
class ContentModel(height: Double, width: Double) {sub =>

  private final var subScene: SubScene = null
  private final val root: Group = new Group

  // camera
  private final val camera: PerspectiveCamera = new PerspectiveCamera(true)
  private final val cameraXform = new Xform()
  private final val cameraXform2 = new Xform()
  private final val cameraXform3 = new Xform()
  private final val cameraDistance: Double = 1500

  // mouse
  private var mousePosX: Double = .0
  private var mousePosY: Double = .0
  private var mouseOldX: Double = .0
  private var mouseOldY: Double = .0
  private var mouseDeltaX: Double = .0
  private var mouseDeltaY: Double = .0

  // content
  private var allCells: List[Node] = List()

  buildCamera()
  buildSubScene()
  //buildAxes()

  private def buildCamera() {

    root.children += cameraXform
    cameraXform.children += cameraXform2
    cameraXform2.children += cameraXform3
    cameraXform3.children += camera
    cameraXform3.rotateZ = 180.0
    camera.nearClip = 0.1
    camera.farClip = 10000.0
    //camera.translateX = -100
    camera.translateZ = -cameraDistance
    cameraXform.ry.angle = 0.0  //320.0
    cameraXform.rx.angle = 90.0  //40
    cameraXform.rz.angle = 0.0  //40
  }

  private def buildSubScene() = {

    // autoscaling group?
    subScene = new SubScene(root, height, width, depthBuffer = true, antiAliasing = SceneAntialiasing.Balanced) {
      fill = Color.Gray
      camera = sub.camera
    }

    handleMouse(subScene, root)

  }

  private def buildAxes() = {

    val redMaterial = new PhongMaterial {
      diffuseColor = Color.DarkRed
      specularColor = Color.Red
    }
    val greenMaterial = new PhongMaterial {
      diffuseColor = Color.DarkGreen
      specularColor = Color.Green
    }
    val blueMaterial = new PhongMaterial {
      diffuseColor = Color.DarkBlue
      specularColor = Color.Blue
    }
    val xAxis = new Box(240.0, 1, 1) {
      material = redMaterial
    }
    val yAxis = new Box(1, 240.0, 1) {
      material = greenMaterial
    }
    val zAxis = new Box(1, 1, 240.0) {
      material = blueMaterial
    }

    root.children ++= Seq(xAxis, yAxis, zAxis)

  }

  def getSubScene() = subScene

  def setContent(content: Node, xTrans: Int, yTrans: Int, zTrans: Int) = {
    allCells = allCells ::: content :: Nil
    allCells.foreach(c => c.setTranslateX(-xTrans))
    root.children.add(content)
  }

  def clearContent = {
    allCells.foreach(c => root.children.remove(c))
  }

  //public void setContent(Node content) { autoScalingGroup.getChildren().add(content);  }

  private def handleMouse(scene: SubScene, root: Node) {
    scene.onMousePressed = (me: MouseEvent) => {
      mousePosX = me.sceneX
      mousePosY = me.sceneY
      mouseOldX = me.sceneX
      mouseOldY = me.sceneY
    }
    scene.onMouseDragged = (me: MouseEvent) => {
      mouseOldX = mousePosX
      mouseOldY = mousePosY
      mousePosX = me.sceneX
      mousePosY = me.sceneY
      mouseDeltaX = mousePosX - mouseOldX
      mouseDeltaY = mousePosY - mouseOldY
      val modifier = if (me.isControlDown) 0.1 else if (me.isShiftDown) 10 else 1.0
      val modifierFactor = 0.1
      if (me.isPrimaryButtonDown) {
        cameraXform.ry.angle = cameraXform.ry.angle() - mouseDeltaX * modifierFactor * modifier * 2.0
        cameraXform.rx.angle = cameraXform.rx.angle() + mouseDeltaY * modifierFactor * modifier * 2.0
      } else if (me.isSecondaryButtonDown) {
        val z = camera.translateZ()
        val newZ = z + mouseDeltaX * modifierFactor * modifier
        camera.translateZ = newZ
      } else if (me.isMiddleButtonDown) {
        cameraXform2.t.x = cameraXform2.t.x() + mouseDeltaX * modifierFactor * modifier * 0.3
        cameraXform2.t.x = cameraXform2.t.y() + mouseDeltaY * modifierFactor * modifier * 0.3
      }
    }
  }
}
