case class Vect(x: Double, y: Double, z: Double) {
  //def +(v: Vect): Vect = Vect(this.x + v.x, this.y + v.y, this.z + v.z)
}

val v1 = Vect(1,1,1)
val v2 = Vect(1,0,0)

val ns = Map(
  "LEFT" -> Vect(0.0, 0.0, 0.0)
  , "RIGHT" -> Vect(-4.0, 2.0, 0.0)
  , "FRONT" -> Vect(1.0, 2.0, 0.0)
  , "BACK" -> Vect(1.0, 2.0, 0.0)
  , "BOTTOM" -> Vect(1.0, 2.0, 1.0)
  , "TOP" -> Vect(1.0, 2.0, -1.0)
)

val influencers = ns.filter(p => influencesMe(p._1, p._2))
val strongest = strongestInfluencer(influencers)


def strongestInfluencer(neighbours: Map[String, Vect]): String = {

  var m = 0.0
  var s = "NONE"

  for {
    ks <- neighbours.keySet
  } ks match {
      case "LEFT" => if (Math.abs(neighbours(ks).x) > m) {m = Math.abs(neighbours(ks).x); s = "LEFT"}
      case "RIGHT" => if (Math.abs(neighbours(ks).x) > m) {m = Math.abs(neighbours(ks).x); s = "RIGHT"}
      case "FRONT" => if (Math.abs(neighbours(ks).y) > m) {m = Math.abs(neighbours(ks).y); s = "FRONT"}
      case "BACK" => if (Math.abs(neighbours(ks).y) > m) {m = Math.abs(neighbours(ks).y); s = "BACK"}
      case "BOTTOM" => if (Math.abs(neighbours(ks).z) > m) {m = Math.abs(neighbours(ks).z); s = "BOTTOM"}
      case "TOP" => if (Math.abs(neighbours(ks).z) > m) {m = Math.abs(neighbours(ks).z); s = "TOP"}
      case _ => ;
    }

  s

}

def influencesMe(site: String, v: Vect): Boolean = {
  site match {
    case "LEFT" if v.x > 0 => true
    case "RIGHT" if v.x < 0 => true
    case "FRONT" if v.y > 0 => true
    case "BACK" if v.y < 0 => true
    case "BOTTOM" if v.z > 0 => true
    case "TOP" if v.z < 0 => true
    case _ => false
  }
}
