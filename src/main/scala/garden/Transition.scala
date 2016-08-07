package garden

/**
  * Created by Basil on 07/08/2016.
  */
sealed trait Transition {
  def rule(cell: Cell): Cell
}

trait Basic extends Transition {
  override def rule(cell: Cell): Cell = RedCellWithNeighbour(cell.index, cell.neighbours)
}

trait GameOfLife extends Transition {

  override def rule(cell: Cell): Cell = {

    val neighbours = cell.neighbours

    cell.state match {

      case _ => {
        val s = {
          def h(acc: Int, cs: List[Cell]): Int = cs match {
            case Nil => acc
            case x :: xs => x match {
              case RedCell(n) => h(acc + 1, xs)
              case _ => h(acc, xs)
            }
          }
          h(0, neighbours.values.toList)
        }
        if (s > 2) RedCellWithNeighbour(cell.index, cell.neighbours) else cell
      }

    }

  }

}

trait BasicGarden extends Transition {
  override def rule(cell: Cell): Cell = {

    val neighbours = cell.neighbours

    cell.state match {

      case SkyState(s, wi, sn, wa, g, v) => {
        neighbours.get("DOWN") match {
          case None => cell
          case Some(c) => c.state match {
            case PlantState(s, wi, sn, wa, g, v) => PlantCell(cell.index, cell.neighbours, wi, sn, wa, g, v)
            case _ => cell
          }
        }
      }
      case _ => cell

    }

  }
}
