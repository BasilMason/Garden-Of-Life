package automaton

import garden._

/**
  * Created by Basil on 13/07/2016.
  */
trait Automaton {
  def next: String
}

//case class Automaton3d(init: String, x: Int, y: Int, z: Int, rule: Cell => Cell) extends Automaton {
case class Automaton3d(init: List[State], x: Int, y: Int, z: Int, rule: Cell => Cell) extends Automaton {

  println(init.length)
  require(x * y * z == init.length)

  val cells: List[Cell] = init.map(s => s match {
    case SkyState(s, w, sn, wa, g, v) => SkyCell(wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case GrassState(s, w, sn, wa, g, v) => GrassCell(wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case EarthState(s, w, sn, wa, g, v) => EarthCell(wind = w, water = wa, sun = sn, gravity = g, velocity = v)
    case PlantState(s, w, sn, wa, g, v) => PlantCell(wind = w, water = wa, sun = sn, gravity = g, velocity = v)
  })

  // form containers
  val planes = cells.grouped(x * y).toList
  val rows = planes.map(p => p.grouped(z).toList).flatten

  // Adjacent Neighbours
  // list of right neighbours
  val rn = rows.flatMap(l => {
    def h(acc: List[Cell], cells: List[Cell]): List[Cell] = cells match {
      case Nil => acc
      case x :: Nil => acc ::: List(addCellNeighbour("RIGHT", x, NilCell()))
      case x :: y :: zs => h(acc ::: List(addCellNeighbour("RIGHT", x, y)), y :: zs)
    }
    h(List(), l)
  })

  // list of left neighbours reversed
  val lnr = rows.flatMap(l => {
    def h(acc: List[Cell], cells: List[Cell]): List[Cell] = cells match {
      case Nil => acc
      case x :: Nil => acc ::: List(addCellNeighbour("LEFT", x, NilCell()))
      case x :: y :: zs => h(acc ::: List(addCellNeighbour("LEFT", x, y)), y :: zs)
    }
    h(List(), l.reverse)
  })
  // list of left neighbours
  val ln = lnr.grouped(z).toList.flatMap(l => l.reverse)

  // list of back neighbour pairs
  val bnp = rows.grouped(y).toList.flatMap(l => {
    def h(acc: List[(Cell, Cell)], cells: List[List[Cell]]): List[(Cell, Cell)] = cells match {
      case Nil => acc
      case x :: Nil => acc ::: (x zip List.fill(x.length)(NilCell()))
      case x :: y :: zs => h(acc ::: (x zip y), y :: zs)
    }
    h(List(), l)
  })
  // list of back neighbours
  val bn = bnp.map(x => addCellNeighbour("BACK", x._1, x._2))

  // list of front neighbour pairs reversed
  val fnp = rows.grouped(y).toList.flatMap(l => {
    def h(acc: List[(Cell, Cell)], cells: List[List[Cell]]): List[(Cell, Cell)] = cells match {
      case Nil => acc
      case x :: Nil => acc ::: (x zip List.fill(x.length)(NilCell()))
      case x :: y :: zs => h(acc ::: (x zip y), y :: zs)
    }
    h(List(), l.reverse)
  })
  // list of front neighbour pairs
  val fnr = fnp.map(x => addCellNeighbour("FRONT", x._1, x._2))
  // list of front neighbours
  val fn = fnr.grouped(y).toList.flatMap(l => l.reverse)
    .grouped(x*y).toList.flatMap(l => l.reverse)

  // list of up neighbour pairs
  val unp = {
    def h(acc: List[(Cell, Cell)], cells: List[List[Cell]]): List[(Cell, Cell)] = cells match {
      case Nil => acc
      case x :: Nil => acc ::: (x zip List.fill(x.length)(NilCell()))
      case x :: y :: zs => h(acc ::: (x zip y), y :: zs)
    }
    h(List(), planes)
  }
  // list of up neighbours
  val un = unp.map(x => addCellNeighbour("UP", x._1, x._2))

  // list of down neighbour pairs reversed
  val dnp = {
    def h(acc: List[(Cell, Cell)], cells: List[List[Cell]]): List[(Cell, Cell)] = cells match {
      case Nil => acc
      case x :: Nil => acc ::: (x zip List.fill(x.length)(NilCell()))
      case x :: y :: zs => h(acc ::: (x zip y), y :: zs)
    }
    h(List(), planes.reverse)
  }
  // list of down neighbour pairs
  val dnr = dnp.map(x => addCellNeighbour("DOWN", x._1, x._2))
  // list of down neighbours
  val dn = dnr.reverse.grouped(x*y).toList.flatMap(l => l.reverse)

  // all adjacent neighbours
  val an = {
    def h(acc: List[Cell], ns: List[List[Cell]]): List[Cell] = ns match {
      case Nil => acc
      case x :: xs => h(for ( (m, a) <- (acc zip x)) yield addCellNeighbourNeighbour(m, a), xs)
    }
    h(rn, List(ln, bn, fn, un, dn))
  }

  // Diagonal Neighbours
  val en = an.map(c => {
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

  def rle(cell: Cell): Cell = {
    val ns = cell.neighbours
    val s = {
      def h(acc: Int, cs: List[Cell]): Int = cs match {
        case Nil => acc
        case x :: xs => x match {
          case RedCell(n) => h(acc + 1, xs)
          case _ => h(acc, xs)
        }
      }
      h(0, ns.values.toList)
    }
    if (s > 2) RedCellWithNeighbour(cell.index, cell.neighbours) else cell
  }

  def getNeighbour(c: Cell, cs: List[Cell], first: String, second: String): Cell = c match {
    case NilCell(i) => NilCell()
    case y => y.neighbours.get(first) match {
      case None => NilCell()
      case Some(y) => y match{
        case NilCell(i) => NilCell()
        case x => {
          val a = cs.filter(c => c.index == x.index).head
          a.neighbours.get(second).get
        }
      }
    }
  }

  def getSecondNeighbour(c: Cell, cs: List[Cell], site: String): Cell = c match {
    case NilCell(i) => NilCell()
    case x => {
      val a = cs.filter(c => c.index == x.index).head
      a.neighbours.get(site).get
    }
  }

  def addCellNeighbour(site: String, c: Cell, n: Cell): Cell = c match {
    case RedCell(i) => RedCellWithNeighbour(i, Map(site -> n))
    case GreenCell(i) => GreenCellWithNeighbour(i, Map(site -> n))
    case BlueCell(i) => BlueCellWithNeighbour(i, Map(site -> n))
    case PadCell(i) => PadCellWithNeighbour(i, Map(site -> n))
  }

  def addCellNeighbourNeighbour(c: Cell, n: Cell): Cell = c match {
    case RedCellWithNeighbour(i, ns) => RedCellWithNeighbour(i, ns ++ n.neighbours)
    case GreenCellWithNeighbour(i, ns) => GreenCellWithNeighbour(i, ns ++ n.neighbours)
    case BlueCellWithNeighbour(i, ns) => BlueCellWithNeighbour(i, ns ++ n.neighbours)
    case PadCellWithNeighbour(i, ns) => PadCellWithNeighbour(i, ns ++ n.neighbours)
  }

  def addCellNeighbourNeighbour(site: String , c: Cell, n: Cell): Cell = c match {
    case RedCellWithNeighbour(i, ns) => RedCellWithNeighbour(i, ns + (site -> n))
    case GreenCellWithNeighbour(i, ns) => GreenCellWithNeighbour(i, ns + (site -> n))
    case BlueCellWithNeighbour(i, ns) => BlueCellWithNeighbour(i, ns + (site -> n))
    case PadCellWithNeighbour(i, ns) => PadCellWithNeighbour(i, ns + (site -> n))
  }

  override def next: String = en.map(c => rle(c)).map(c => c match {
    case RedCellWithNeighbour(i, ns) => "R"
    case GreenCellWithNeighbour(i, ns) => "G"
    case BlueCellWithNeighbour(i, ns) => "B"
    case PadCellWithNeighbour(i, ns) => "P"
  }).mkString

}