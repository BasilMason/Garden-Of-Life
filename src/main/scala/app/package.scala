import garden.State
import viewer.Cell

import scalafx.geometry.Insets
import scalafx.scene.layout.HBox

/**
  * Created by Basil on 17/08/2016.
  */
package object app {

  // Application
  final val APP_WIDTH = 1500
  final val APP_HEIGHT = 1200
  final val TOOLBAR_HEIGHT = 20

  // Garden
  def X_DIM = 50
  def Y_DIM = 50
  def Z_DIM = 15

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

}
