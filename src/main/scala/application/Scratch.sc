sealed trait VectorN
case class Vector1(x: Int) extends VectorN
case class Vector2(x: Int, y: Int) extends VectorN
case class Vector3(x: Int, y: Int, z: Int) extends VectorN

def influencesMe2(site: String, v: List[Vector3]): (Boolean, Vector3) = {

  var infl = false
  var infls = Vector3(0,0,0)

  for {
    vs <- v
  } {
    if (!infl) {
      site match {
        case "LEFT" if vs.x > 0 => {
          infl = true
          infls = vs
        }
        case "RIGHT" if vs.x < 0 => {
          infl = true
          infls = vs
        }
        case "FRONT" if vs.z > 0 => {
          infl = true
          infls = vs
        }
        case "BACK" if vs.z < 0 => {
          infl = true
          infls = vs
        }
        case "BOTTOM" if vs.y > 0 => {
          infl = true
          infls = vs
        }
        case "TOP" if vs.y < 0 => {
          infl = true
          infls = vs
        }
        case _ => {
          infl = false
        }
      }
    }
  }

  (infl,infls)

}

def strongestInfluencer2(ns: List[(String, Boolean, Vector3)]): String = {

  var strongest = 0
  var site = "NONE"

  for {
    n <- ns
  } {

    n._1 match {
      case "LEFT" if Math.abs(n._3.x) > Math.abs(strongest) => {
        strongest = n._3.x
        site = "LEFT"
      }
      case "RIGHT" if Math.abs(n._3.x) > Math.abs(strongest) => {
        strongest = n._3.x
        site = "RIGHT"
      }
      case "TOP" if Math.abs(n._3.y) > Math.abs(strongest) => {
        strongest = n._3.y
        site = "TOP"
      }
      case "BOTTOM" if Math.abs(n._3.y) > Math.abs(strongest) => {
        strongest = n._3.y
        site = "BOTTOM"
      }
      case "BACK" if Math.abs(n._3.z) > Math.abs(strongest) => {
        strongest = n._3.z
        site = "BACK"
      }
      case "FRONT" if Math.abs(n._3.z) > Math.abs(strongest) => {
        strongest = n._3.z
        site = "FRONT"
      }
      case _ => ;

    }

  }

  site

}

val ns = Map(
  "LEFT" -> List(Vector3(0,0,0), Vector3(0,0,0)),
  "RIGHT" -> List(Vector3(0,0,0), Vector3(0,0,0)),
  "TOP" -> List(Vector3(0,0,0), Vector3(0,0,0)),
  "BOTTOM" -> List(Vector3(0,0,0), Vector3(0,0,0)),
  "BACK" -> List(Vector3(0,0,0), Vector3(0,0,0)),
  "FRONT" -> List(Vector3(0,0,0), Vector3(0,0,0))
)


val nns = ns.map(p => {
  val infl = influencesMe(p._1, p._2)
  (p._1, infl._1, infl._2)
})


val nnns = nns.filter(p => p._2 == true).toList

val strong = strongestInfluencer(nnns)




