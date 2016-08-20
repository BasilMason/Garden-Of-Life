package application.viewer

import scalafx.scene.paint.{Color, PhongMaterial}
import scalafx.scene.shape.Box

/**
  * Created by Basil on 13/07/2016.
  */
case class Cell(state: String) extends Box {cell =>

  cell.width = 10
  cell.height = 10
  cell.depth = 10

  state match {

    case "G" => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.DarkGreen
        specularColor = Color.Green
      }
    }
    case "B" => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.DarkBlue
        specularColor = Color.Blue
      }
    }
    case "R" => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.DarkRed
        specularColor = Color.Purple
      }
    }
    case "P" => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.Transparent
        specularColor = Color.Transparent
        visible = false
      }
    }
    case "PS" => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.LightGreen
        specularColor = Color.LightGreen
        visible = true
      }
    }
    case "ES" => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.Brown
        specularColor = Color.Brown
        visible = true
      }
    }
    case "GS" => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.DarkGreen
        specularColor = Color.DarkGreen
        visible = true
      }
    }
    case "SS" => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.Transparent
        specularColor = Color.Transparent
        visible = false
//        diffuseColor = Color.Blue
//        specularColor = Color.Blue
//        visible = true
      }
    }
    case "FS" => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.HotPink
        specularColor = Color.HotPink
        visible = true
      }
    }
    case "NS" => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.Yellow
        specularColor = Color.LightYellow
        visible = true
      }
    }
    case "TS" => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.RosyBrown
        specularColor = Color.RosyBrown
        visible = true
      }
    }
    case "CS" => {
      cell.material = new PhongMaterial() {
        diffuseColor = Color.LightGray
        specularColor = Color.LightGray
        visible = true
      }
    }

  }

}
