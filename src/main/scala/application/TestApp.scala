package application

import automaton.garden.Config

/**
  * Created by Basil on 07/08/2016.
  */
object TestApp extends App {

  println("Gardern Testing App!")

  val x = 5
  val y = 5
  val z = 5
  val init = Config.classic(x, y, z)

  println("Init: " + init)

}
