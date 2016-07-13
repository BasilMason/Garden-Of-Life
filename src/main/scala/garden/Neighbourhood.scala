package garden

/**
  * Created by Basil on 13/07/2016.
  */
trait Neighbourhood {
  def neighbours: Map[String, Cell]
}
