package application.viewer

import automaton.garden._
import newauto._

import scalafx.scene.paint.{Color, PhongMaterial}
import scalafx.scene.shape.Box

/**
  * Created by Basil on 13/07/2016.
  */
case class Cell(state: State) extends Box {cell =>

  cell.width = 10
  cell.height = 10
  cell.depth = 10

  state match {

    case GreenState => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.DarkGreen
        specularColor = Color.Green
      }
    }
    case BlueState => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.DarkBlue
        specularColor = Color.Blue
      }
    }
    case RedState => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.DarkRed
        specularColor = Color.Purple
      }
    }
    case Alive => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.DarkBlue
        specularColor = Color.Blue
      }
    }
    case Dead => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.DarkRed
        specularColor = Color.Purple
      }
    }
    case PadState => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.Transparent
        specularColor = Color.Transparent
        visible = false
      }
    }
    case PlantState(wi, sn, wa, g, v, a, vlm) => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.LightGreen
        specularColor = Color.LightGreen
        visible = true
      }
    }
    case EarthState(wi, sn, wa, g, v, a, vlm) => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.Brown
        specularColor = Color.Brown
        visible = true
      }
    }
    case GrassState(wi, sn, wa, g, v, a, vlm) => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.DarkGreen
        specularColor = Color.DarkGreen
        visible = true
      }
    }
    case SkyState(wi, sn, wa, g, v, a, vlm) => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.Transparent
        specularColor = Color.Transparent
        visible = false
//        diffuseColor = Color.Blue
//        specularColor = Color.Blue
//        visible = true
      }
    }
    case FlowerState(wi, sn, wa, g, v, a, vlm) => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.HotPink
        specularColor = Color.HotPink
        visible = true
      }
    }
    case SunState(wi, sn, wa, g, v, a, vlm) => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.rgb(255, 173, 1)
        specularColor = Color.LightYellow
        visible = true
      }
    }
    case TreeState(wi, sn, wa, g, v, a, vlm) => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.RosyBrown
        specularColor = Color.RosyBrown
        visible = true
      }
    }
    case CloudState(wi, sn, wa, g, v, a, vlm) => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.LightGray
        specularColor = Color.LightGray
        visible = true
      }
    }
    case VoidState(wi, sn, wa, g, v, a, vlm) => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.Transparent
        specularColor = Color.Transparent
        visible = false
      }
    }

  }

}

case class NCellN(state: NState) extends Box {cell =>

  cell.width = 10
  cell.height = 10
  cell.depth = 10

  state match {
    case NAlive => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.DarkBlue
        specularColor = Color.Blue
      }
    }
    case NDead => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.DarkRed
        specularColor = Color.Purple
        visible = false
      }
    }
    case NSky(wa, sn, wi) => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.Transparent
        specularColor = Color.Transparent
        visible = false
      }
    }
    case NSoil(wa, sn, wi) => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.Brown
        specularColor = Color.Brown
        visible = true
      }
    }
    case NGrass(wa, sn, wi, vl, ag, vm) => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.DarkGreen
        specularColor = Color.DarkGreen
        visible = true
      }
    }
    case NPlant(wa, sn, wi, vl, ag, vm) => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.LightGreen
        specularColor = Color.LightGreen
        visible = true
      }
    }
    case NTree(wa, sn, wi, vl, ag, vm) => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.SaddleBrown
        specularColor = Color.SaddleBrown
        visible = true
      }
    }
  }
}
