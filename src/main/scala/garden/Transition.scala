package garden

/**
  * Created by Basil on 07/08/2016.
  *
  *
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

/**
  * Basic Garden Rules
  *
  * - Earth states have no velocity, gravity (in effect), sun or wind, but 1.0 water which depletes if neighbouring cell is Plant
  *   state, once zero nothing can grow.
  * - Sky state carries sun and wind, no water or gravity (in effect). Sky becomes whichever neighbouring plant cell
  *   has the highest velocity in its direction.
  * - Grass states soak up a small amount of water and grow and die quickly. Not affected by gravity or sunlight or wind.
  * - Plant grows up with depleting velocity, flowers when zero then slowly dies. Takes up medium water, affected by wind
  *   and gravity and sun.
  * - Tree?
  *
  *
  * Utilty to get stat from all neighbours
  *
  */
trait BasicGarden extends Transition {
  override def rule(cell: Cell): Cell = {

    val neighbours = cell.neighbours

    // propagate age and volume...

    cell.state match {

      // Sky
      case SkyState(s, wi, sn, wa, g, v) => {
        neighbours.get("DOWN") match {
          case None => cell
          case Some(c) => c.state match {
            case PlantState(s, wi, sn, wa, g, v) => PlantCell(cell.index, cell.neighbours, wi, sn, wa, g, v)
            case _ => {
              neighbours.get("LEFT") match {
                case None => cell
                case Some(c) => c.state match {
                  case PlantState(s, wi, sn, wa, g, v) => PlantCell(cell.index, cell.neighbours, wi, sn, wa, g, v)
                  case _ => cell
                }

              }
            }
          }
        }
      }

      // Earth
      case EarthState(s, wi, sn, wa, g, v) => {
        val all = neighbours.values
        val consumption = all.map(c => c match {
          case PlantCell(i, nei, w, sn, wa, g, v) => 0.2
          case GrassCell(i, nei, w, sn, wa, g, v) => 0.05
          case _ => 0.0
        }).sum
        EarthCell(cell.index, cell.neighbours, wi, sn, wa - consumption, g, v)
      }

      // Grass
      case GrassState(s, wi, sn, wa, g, v) => {
        GrassCell(cell.index, cell.neighbours, wi, sn, wa, g, (v._1, v._2 - 0.2, v._3))
      }

      case PlantState(s, wi, sn, wa, g, v) => {
        if (v._2 < 0.5) FlowerCell(cell.index, cell.neighbours, wi, sn, wa, g, (v._1, v._2 - 0.2, v._3))
        else PlantCell(cell.index, cell.neighbours, wi, sn, wa, g, (v._1, v._2 - 0.2, v._3))
      }

      case _ => cell

    }

  }
}
