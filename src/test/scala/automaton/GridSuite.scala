package automaton

import newauto._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GridSuite extends FunSuite {

  trait TestGrids {

    val gb = GridBuilder
    val emptyGrid = GridBuilder.oneDimensionalGrid(0)(List.empty)
    val l1 = List(NewCell(NDead, (s,ns)=>s), NewCell(NDead, (s,ns)=>s), NewCell(NDead, (s,ns)=>s), NewCell(NAlive, (s,ns)=>s))
    val l2 = List(NewCell(NDead, (s,ns)=>s), NewCell(NDead, (s,ns)=>s), NewCell(NDead, (s,ns)=>s), NewCell(NAlive, (s,ns)=>s),NewCell(NDead, (s,ns)=>s),NewCell(NAlive, (s,ns)=>s),NewCell(NDead, (s,ns)=>s),NewCell(NAlive, (s,ns)=>s))
    val g1 = gb.oneDimensionalGrid(4)(l1)
    val g2 = gb.twoDimenionalgrid(2,2)(l1)
    val g3 = gb.threeDimenionalgrid(2,2,2)(l2)

  }

  test("Zero dimension grid should be empty") {
    new TestGrids {
      assert(emptyGrid.size == 0)
    }
  }

  test("Grid dimensions must match input, exception check") {
    new TestGrids {
      intercept[IllegalArgumentException] {
        GridBuilder.oneDimensionalGrid(1)(List(new NewCell(), new NewCell()))
      }
      intercept[IllegalArgumentException] {
        GridBuilder.twoDimenionalgrid(1,1)(List(new NewCell(), new NewCell()))
      }
      intercept[IllegalArgumentException] {
        GridBuilder.threeDimenionalgrid(1,1,1)(List(new NewCell(), new NewCell()))
      }
    }
  }

  test("Alive cell should be at given location") {
    new TestGrids {

      // 1D
      assert(g1(Vector1(0)).currentState == NDead, "Location 0 should be dead")
      assert(g1(Vector1(1)).currentState == NDead, "Location 1 should be dead")
      assert(g1(Vector1(2)).currentState == NDead, "Location 2 should be dead")
      assert(g1(Vector1(3)).currentState == NAlive, "Location 3 should be alive")

      // 2D
      assert(g2(Vector2(0,0)).currentState == NDead, "Location (0,0) should be dead")
      assert(g2(Vector2(1,0)).currentState == NDead, "Location (1,0) should be dead")
      assert(g2(Vector2(0,1)).currentState == NDead, "Location (0,1) should be dead")
      assert(g2(Vector2(1,1)).currentState == NAlive, "Location (1,1) should be alive")

      // 3D
      assert(g3(Vector3(0,0,0)).currentState == NDead, "Location (0,0,0) should be dead")
      assert(g3(Vector3(1,0,0)).currentState == NDead, "Location (1,0,0) should be dead")
      assert(g3(Vector3(0,1,0)).currentState == NDead, "Location (0,1,0) should be dead")
      assert(g3(Vector3(1,1,0)).currentState == NAlive, "Location (1,1,0) should be dead")
      assert(g3(Vector3(0,0,1)).currentState == NDead, "Location (0,0,1) should be dead")
      assert(g3(Vector3(1,0,1)).currentState == NAlive, "Location (1,0,1) should be alive")
      assert(g3(Vector3(0,1,1)).currentState == NDead, "Location (0,1,1) should be dead")
      assert(g3(Vector3(1,1,1)).currentState == NAlive, "Location (1,1,1) should be alive")

    }
  }


}
