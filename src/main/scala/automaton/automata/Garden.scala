package automaton.automata

import automaton.garden._

/**
  * Created by Basil on 20/08/2016.
  */
case class Garden(init: List[State], x: Int, y: Int, z: Int) extends Automata with BasicGarden {

  require(x * y * z == init.length)

  private val cells: List[CellX] = init.map(s => s match {
    case SkyState(s, w, sn, wa, g, v) => SkyCellX(wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case GrassState(s, w, sn, wa, g, v) => GrassCellX(wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case EarthState(s, w, sn, wa, g, v) => EarthCellX(wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case PlantState(s, w, sn, wa, g, v) => PlantCellX(wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case FlowerState(s, w, sn, wa, g, v) => FlowerCellX(wind = w, water = wa, sun = sn, gravity = g, velocity = v)

  })

  // form containers
  private val planes = cells.grouped(x * y).toList
  private val rows = planes.map(p => p.grouped(z).toList).flatten

  // Adjacent Neighbours
  // list of right neighbours
  private val rn = rows.flatMap(l => {
    def h(acc: List[CellX], cells: List[CellX]): List[CellX] = cells match {
      case Nil => acc
      case x :: Nil => acc ::: List(addCellNeighbour("RIGHT", x, NilCellX()))
      case x :: y :: zs => h(acc ::: List(addCellNeighbour("RIGHT", x, y)), y :: zs)
    }
    h(List(), l)
  })

  // list of left neighbours reversed
  private val lnr = rows.flatMap(l => {
    def h(acc: List[CellX], cells: List[CellX]): List[CellX] = cells match {
      case Nil => acc
      case x :: Nil => acc ::: List(addCellNeighbour("LEFT", x, NilCellX()))
      case x :: y :: zs => h(acc ::: List(addCellNeighbour("LEFT", x, y)), y :: zs)
    }
    h(List(), l.reverse)
  })
  // list of left neighbours
  private val ln = lnr.grouped(z).toList.flatMap(l => l.reverse)

  // list of back neighbour pairs
  private val bnp = rows.grouped(y).toList.flatMap(l => {
    def h(acc: List[(CellX, CellX)], cells: List[List[CellX]]): List[(CellX, CellX)] = cells match {
      case Nil => acc
      case x :: Nil => acc ::: (x zip List.fill(x.length)(NilCellX()))
      case x :: y :: zs => h(acc ::: (x zip y), y :: zs)
    }
    h(List(), l)
  })
  // list of back neighbours
  private val bn = bnp.map(x => addCellNeighbour("BACK", x._1, x._2))

  // list of front neighbour pairs reversed
  private val fnp = rows.grouped(y).toList.flatMap(l => {
    def h(acc: List[(CellX, CellX)], cells: List[List[CellX]]): List[(CellX, CellX)] = cells match {
      case Nil => acc
      case x :: Nil => acc ::: (x zip List.fill(x.length)(NilCellX()))
      case x :: y :: zs => h(acc ::: (x zip y), y :: zs)
    }
    h(List(), l.reverse)
  })
  // list of front neighbour pairs
  private val fnr = fnp.map(x => addCellNeighbour("FRONT", x._1, x._2))
  // list of front neighbours
  private val fn = fnr.grouped(y).toList.flatMap(l => l.reverse)
    .grouped(x*y).toList.flatMap(l => l.reverse)

  // list of up neighbour pairs
  private val unp = {
    def h(acc: List[(CellX, CellX)], cells: List[List[CellX]]): List[(CellX, CellX)] = cells match {
      case Nil => acc
      case x :: Nil => acc ::: (x zip List.fill(x.length)(NilCellX()))
      case x :: y :: zs => h(acc ::: (x zip y), y :: zs)
    }
    h(List(), planes)
  }
  // list of up neighbours
  private val un = unp.map(x => addCellNeighbour("UP", x._1, x._2))

  // list of down neighbour pairs reversed
  private val dnp = {
    def h(acc: List[(CellX, CellX)], cells: List[List[CellX]]): List[(CellX, CellX)] = cells match {
      case Nil => acc
      case x :: Nil => acc ::: (x zip List.fill(x.length)(NilCellX()))
      case x :: y :: zs => h(acc ::: (x zip y), y :: zs)
    }
    h(List(), planes.reverse)
  }
  // list of down neighbour pairs
  private val dnr = dnp.map(x => addCellNeighbour("DOWN", x._1, x._2))
  // list of down neighbours
  private val dn = dnr.reverse.grouped(x*y).toList.flatMap(l => l.reverse)

  // all adjacent neighbours
  private val an = {
    def h(acc: List[CellX], ns: List[List[CellX]]): List[CellX] = ns match {
      case Nil => acc
      case x :: xs => h(for ( (m, a) <- (acc zip x)) yield addCellNeighbourNeighbour(m, a), xs)
    }
    h(rn, List(ln, bn, fn, un, dn))
  }

  // Diagonal Neighbours
  private val en = an.map(c => {
    val crnbn = addCellNeighbourNeighbour("RIGHT_BACK", c, getNeighbour(c, bn, "RIGHT", "BACK"))
    val crnfn = addCellNeighbourNeighbour("RIGHT_FRONT", crnbn, getNeighbour(c, fn, "RIGHT", "FRONT"))
    val clnbn = addCellNeighbourNeighbour("LEFT_BACK", crnfn, getNeighbour(c, bn, "LEFT", "BACK"))
    val clnfn = addCellNeighbourNeighbour("LEFT_FRONT", clnbn, getNeighbour(c, fn, "LEFT", "FRONT"))
    val cunln = addCellNeighbourNeighbour("UP_LEFT", clnfn, getNeighbour(c, ln, "UP", "LEFT"))
    val cunlnbn = addCellNeighbourNeighbour("UP_LEFT_BACK", cunln, getSecondNeighbour(getNeighbour(c, ln, "UP", "LEFT"), bn, "BACK"))
    val cunlnfn = addCellNeighbourNeighbour("UP_LEFT_FRONT", cunlnbn, getSecondNeighbour(getNeighbour(c, ln, "UP", "LEFT"), fn, "FRONT"))
    val cunrn = addCellNeighbourNeighbour("UP_RIGHT", cunlnfn, getNeighbour(c, rn, "UP", "RIGHT"))
    val cunrnbn = addCellNeighbourNeighbour("UP_RIGHT_BACK", cunrn, getSecondNeighbour(getNeighbour(c, rn, "UP", "RIGHT"), bn, "BACK"))
    val cunrnfn = addCellNeighbourNeighbour("UP_RIGHT_FRONT", cunrnbn, getSecondNeighbour(getNeighbour(c, rn, "UP", "RIGHT"), fn, "FRONT"))
    val cunbn = addCellNeighbourNeighbour("UP_BACK", cunrnfn, getNeighbour(c, bn, "UP", "BACK"))
    val cunfn = addCellNeighbourNeighbour("UP_FRONT", cunbn, getNeighbour(c, fn, "UP", "FRONT"))
    val cdnln = addCellNeighbourNeighbour("DOWN_LEFT", cunfn, getNeighbour(c, ln, "DOWN", "LEFT"))
    val cdnlnbn = addCellNeighbourNeighbour("DOWN_LEFT_BACK", cdnln, getSecondNeighbour(getNeighbour(c, ln, "DOWN", "LEFT"), bn, "BACK"))
    val cdnlnfn = addCellNeighbourNeighbour("DOWN_LEFT_FRONT", cdnlnbn, getSecondNeighbour(getNeighbour(c, ln, "DOWN", "LEFT"), fn, "FRONT"))
    val cdnrn = addCellNeighbourNeighbour("DOWN_RIGHT", cdnlnfn, getNeighbour(c, rn, "DOWN", "RIGHT"))
    val cdnrnbn = addCellNeighbourNeighbour("DOWN_RIGHT_BACK", cdnrn, getSecondNeighbour(getNeighbour(c, rn, "DOWN", "RIGHT"), bn, "BACK"))
    val cdnrnfn = addCellNeighbourNeighbour("DOWN_RIGHT_FRONT", cdnrnbn, getSecondNeighbour(getNeighbour(c, rn, "DOWN", "RIGHT"), fn, "FRONT"))
    val cdnbn = addCellNeighbourNeighbour("DOWN_BACK", cdnrnfn, getNeighbour(c, bn, "DOWN", "BACK"))
    val cdnfn = addCellNeighbourNeighbour("DOWN_FRONT", cdnbn, getNeighbour(c, fn, "DOWN", "FRONT"))
    cdnfn
  })

  private def getNeighbour(c: CellX, cs: List[CellX], first: String, second: String): CellX = c match {
    case NilCellX(i) => NilCellX()
    case y => y.neighbours.get(first) match {
      case None => NilCellX()
      case Some(y) => y match{
        case NilCellX(i) => NilCellX()
        case x => {
          val a = cs.filter(c => c.index == x.index).head
          a.neighbours.get(second).get
        }
      }
    }
  }

  private def getSecondNeighbour(c: CellX, cs: List[CellX], site: String): CellX = c match {
    case NilCellX(i) => NilCellX()
    case x => {
      val a = cs.filter(c => c.index == x.index).head
      a.neighbours.get(site).get
    }
  }

  private def addCellNeighbour(site: String, c: CellX, n: CellX): CellX = c match {
    case RedCellX(i) => RedCellXWithNeighbour(i, Map(site -> n))
    case GreenCellX(i) => GreenCellXWithNeighbour(i, Map(site -> n))
    case BlueCellX(i) => BlueCellXWithNeighbour(i, Map(site -> n))
    case PadCellX(i) => PadCellXWithNeighbour(i, Map(site -> n))
    case SkyCellX(i, nei, w, sn, wa, g, v) => SkyCellX(index = i, neighbours = Map(site -> n), wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case GrassCellX(i, nei, w, sn, wa, g, v) => GrassCellX(index = i, neighbours = Map(site -> n), wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case EarthCellX(i, nei, w, sn, wa, g, v) => EarthCellX(index = i, neighbours = Map(site -> n), wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case PlantCellX(i, nei, w, sn, wa, g, v) => PlantCellX(index = i, neighbours = Map(site -> n), wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case FlowerCellX(i, nei, w, sn, wa, g, v) => FlowerCellX(index = i, neighbours = Map(site -> n), wind = w, water = wa, sun = sn, gravity = g, velocity = v)
  }

  private def addCellNeighbourNeighbour(c: CellX, n: CellX): CellX = c match {
    case RedCellXWithNeighbour(i, ns) => RedCellXWithNeighbour(i, ns ++ n.neighbours)
    case GreenCellXWithNeighbour(i, ns) => GreenCellXWithNeighbour(i, ns ++ n.neighbours)
    case BlueCellXWithNeighbour(i, ns) => BlueCellXWithNeighbour(i, ns ++ n.neighbours)
    case PadCellXWithNeighbour(i, ns) => PadCellXWithNeighbour(i, ns ++ n.neighbours)
    case SkyCellX(i, nei, w, sn, wa, g, v) => SkyCellX(index = i, neighbours = nei ++ n.neighbours, wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case GrassCellX(i, nei, w, sn, wa, g, v) => GrassCellX(index = i, neighbours = nei ++ n.neighbours, wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case EarthCellX(i, nei, w, sn, wa, g, v) => EarthCellX(index = i, neighbours = nei ++ n.neighbours, wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case PlantCellX(i, nei, w, sn, wa, g, v) => PlantCellX(index = i, neighbours = nei ++ n.neighbours, wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case FlowerCellX(i, nei, w, sn, wa, g, v) => FlowerCellX(index = i, neighbours = nei ++ n.neighbours, wind = w, water = wa, sun = sn, gravity = g, velocity = v)
  }

  private def addCellNeighbourNeighbour(site: String , c: CellX, n: CellX): CellX = c match {
    case RedCellXWithNeighbour(i, ns) => RedCellXWithNeighbour(i, ns + (site -> n))
    case GreenCellXWithNeighbour(i, ns) => GreenCellXWithNeighbour(i, ns + (site -> n))
    case BlueCellXWithNeighbour(i, ns) => BlueCellXWithNeighbour(i, ns + (site -> n))
    case PadCellXWithNeighbour(i, ns) => PadCellXWithNeighbour(i, ns + (site -> n))
    case SkyCellX(i, nei, w, sn, wa, g, v) => SkyCellX(index = i, neighbours = nei + (site -> n), wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case GrassCellX(i, nei, w, sn, wa, g, v) => GrassCellX(index = i, neighbours = nei + (site -> n), wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case EarthCellX(i, nei, w, sn, wa, g, v) => EarthCellX(index = i, neighbours = nei + (site -> n), wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case PlantCellX(i, nei, w, sn, wa, g, v) => PlantCellX(index = i, neighbours = nei + (site -> n), wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case FlowerCellX(i, nei, w, sn, wa, g, v) => FlowerCellX(index = i, neighbours = nei + (site -> n), wind = w, water = wa, sun = sn, gravity = g, velocity = v)
  }

  override def next: List[State] = en.map(c => rule(c)).map(c => c.state)

}
