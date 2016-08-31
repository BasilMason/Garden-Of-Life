package newauto

/**
  * Created by Basil on 31/08/2016.
  */
sealed trait VectorN
case class Vector1(x: Int) extends VectorN
case class Vector2(x: Int, y: Int) extends VectorN
case class Vector3(x: Int, y: Int, z: Int) extends VectorN
