package newauto

import parallel.TaskManager

/**
  * Created by Basil on 31/08/2016.
  */
object TraversalBuilder {

  def oneDimensionalTraversal(x: Int): (Grid, Neighbourhood) => List[NCell] = (g, ns) => {
    //in.map(c => NewCell(c.transitionFunction(c.currentState, ns), c.transitionFunction))

    val l = for {
      xs <- (0 until x)
      c = g(Vector1(xs))
      n = ns(Vector1(xs))
    } yield NewCell(c.transitionFunction(c.currentState, n), c.transitionFunction)

    l.toList

  }

  def threeDimensionalTraversal(x: Int, y: Int, z: Int): (Grid, Neighbourhood) => List[NCell] = (g, ns) => {

    val l = for {
      zs <- 0 until z   // each plane
      ys <- 0 until y   // each row
      xs <- 0 until x   // each cell
      c = g(Vector3(xs, ys, zs))    // the cell
      n = ns(Vector3(xs, ys, zs))   // its neighbours
    } yield NewCell(c.transitionFunction(c.currentState, n), c.transitionFunction)  // the next state

    l.toList

  }

  def threeDimensionalTraversalPar(x: Int, y: Int, z: Int)(t: Int): (Grid, Neighbourhood) => List[NCell] = (g, ns) => {

    def traverse(beginning: Int, end: Int): List[NCell] = {

      val l = for {
        zs <- beginning until end
        ys <- 0 until y
        xs <- 0 until x
        c = g(Vector3(xs, ys, zs))
        n = ns(Vector3(xs, ys, zs))
      } yield {

        val s = c.transitionFunction(c.currentState, n)
        val t = functionFromState(s)

        NewCell(s, t)
      }

      l.toList

    }

    def reduce(beginning: Int, end: Int, threshold: Int): List[NCell] = {

      if ((end - beginning) < threshold) {
        traverse(beginning, end)
      } else {
        val m = ((end - beginning) / 2) + beginning
        val (t1, t2) = TaskManager.parallel(reduce(beginning, m, threshold), reduce(m, end, threshold))
        t1 ::: t2
      }

    }

    reduce(0, z, t)
  }

  def threeDimensionalTraversalDP(x: Int, y: Int, z: Int)(t: Int): (Grid, Neighbourhood) => List[NCell] = (g, ns) => {

    val vs = for {
      zs <- 0 until z
      ys <- 0 until y
      xs <- 0 until x
    } yield Vector3(xs,ys,zs)

    val cs = for (v <- vs.par) yield (g(v), ns(v)) // get cell and neighbours
    val l = for (p <- cs.par) yield NewCell(p._1.transitionFunction(p._1.currentState, p._2), p._1.transitionFunction) // call transition function

    l.toList

  }

  def functionFromState(s: NState): Transition = s match {
    case NSoil(wa,sn,wi) => TransitionBuilder.basicSoil
    case NSky(wa,sn,wi) => TransitionBuilder.dynamicSky
    case NGrass(wa,sn,wi,vl,ag,vm) => TransitionBuilder.basicGrass
    case NPlant(wa,sn,wi,vl,ag,vm) => TransitionBuilder.basicPlant
    case NTree(wa,sn,wi,vl,ag,vm) => TransitionBuilder.basicTree
  }

}
