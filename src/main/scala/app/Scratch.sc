val x = List(1,2)
val y = List(3,4)
val z = List(5,6)
val t = 3
val m = Map(0 -> x, 1 -> y, 2 -> z)

val res = for (i <- (0 until t)) yield m(i)
val fin = res.toList.flatten



