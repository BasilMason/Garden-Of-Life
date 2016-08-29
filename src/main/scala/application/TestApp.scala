package application

import automaton.automata.OneDimensional
import automaton.garden.Config

/**
  * Created by Basil on 07/08/2016.
  */
object TestApp extends App {

  println("Gardern Testing App!")

  val x = 9
  val init = Config.oneDimensional(x)

  println("Init: " + init)

  val n1 = OneDimensional(init, x).next
  val n2 = OneDimensional(n1, x).next
  val n3 = OneDimensional(n2, x).next

  println(init)
  println(n1)
  println(n2)
  println(n3)


}
