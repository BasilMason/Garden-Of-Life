import newauto.{GridBuilder, NAlive, NDead, NewCell}

val gb = GridBuilder
val l1 = List(NewCell(NDead, (s, ns)=>s), NewCell(NDead, (s, ns)=>s), NewCell(NDead, (s, ns)=>s), NewCell(NAlive, (s, ns)=>s))
val g1 = gb.oneDimensionalGrid(4)(l1)
