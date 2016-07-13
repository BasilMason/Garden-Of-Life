package garden

/**
  * Created by Basil on 13/07/2016.
  */
object Generator {
  private var id: Int = 0
  def get(): Int = {
    id += 1
    id
  }
}
