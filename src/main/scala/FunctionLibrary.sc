
// Map a map...
def mapToMap[A,B,C](in: Map[A, B], op: B => C): Map[A, C] = {
  def h(acc: Map[A, C], ks: Set[A]): Map[A, C] = ks.toList match {
    case Nil => acc
    case x :: xs => {
      val v = in(x)
      val e = op(v)
      h(acc + (x -> e), xs.toSet)
    }
  }
  h(Map.empty[A, C], in.keySet)
}

val m = Map("a" -> 1, "b" -> 2)
val n = mapToMap[String, Int, Int](m, x => x * x)

val g = m.getOrElse("c", 1)

