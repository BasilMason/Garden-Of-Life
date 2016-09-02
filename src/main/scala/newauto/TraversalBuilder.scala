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
      zs <- (0 until z)
      ys <- (0 until y)
      xs <- (0 until x)
      c = g(Vector3(xs, ys, zs))
      n = ns(Vector3(xs, ys, zs))
    } yield NewCell(c.transitionFunction(c.currentState, n), c.transitionFunction)

    l.toList

  }

  def threeDimensionalTraversalPar(x: Int, y: Int, z: Int)(t: Int): (Grid, Neighbourhood) => List[NCell] = (g, ns) => {

    def traverse(beginning: Int, end: Int): List[NCell] = {

      val l = for {
        zs <- (beginning until end)
        ys <- (0 until y)
        xs <- (0 until x)
        c = g(Vector3(xs, ys, zs))
        n = ns(Vector3(xs, ys, zs))
      } yield NewCell(c.transitionFunction(c.currentState, n), c.transitionFunction)

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
}
