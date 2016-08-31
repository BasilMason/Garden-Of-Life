import automaton.garden.State
import application.viewer.{Cell, NCellN}
import newauto.NState

import scalafx.geometry.Insets
import scalafx.scene.layout.HBox

/**
  * Created by Basil on 17/08/2016.
  */
package object application {

  // Application
  final val APP_WIDTH = 1500
  final val APP_HEIGHT = 1200
  final val TOOLBAR_HEIGHT = 20

  // Garden
  def X_DIM = 50
  def Y_DIM = 50
  def Z_DIM = 50

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

  def row(x: Int, conf: List[State]): List[Cell] = {

    val acc = List()
    val z = 0

    def h(acc: List[Cell], length: Int, z: Int, conf: List[State]): List[Cell] = length match {

      case 0 => acc
      case _ => {
        val s = conf.head
        val c = Cell(s)
        c.translateX = z
        List(c) ::: h(acc, length - 1, z + 15, conf.tail)}
    }

    h(acc, x, z, conf)

  }

  def rowN(x: Int, conf: List[NState]): List[NCellN] = {

    val acc = List()
    val z = 0

    def h(acc: List[NCellN], length: Int, z: Int, conf: List[NState]): List[NCellN] = length match {

      case 0 => acc
      case _ => {
        val s = conf.head
        val c = NCellN(s)
        c.translateX = z
        List(c) ::: h(acc, length - 1, z + 15, conf.tail)}
    }

    h(acc, x, z, conf)

  }

}
