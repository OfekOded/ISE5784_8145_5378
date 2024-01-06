package primitives;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Point
 * @author Oded, Naor
 */

class PointTests {

    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks the integrity of add function
        assertEquals(new Point(2,4,6),new Point(1,2,3).add(new Vector(1,2,3)),"ERROR: (point + vector) = other point does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC10: Checks in case the result is the zero vector
        assertEquals(new Point(0,0,0),new Point(1,2,3).add(new Vector(-1,-2,-3)),"ERROR: (point + vector) = other point does not work correctly");
    }

    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01:Checks the integrity of subtract points
        assertEquals(new Vector(1, 2, 3),new Point(2, 4, 6).subtract(new Point(1, 2, 3)),"ERROR: (point2 - point1) does not work correctly");
        // =============== Boundary Values Tests ==================
        // TC10: Checks in case the result is the zero vector
        assertThrows(IllegalArgumentException.class,()-> new Point(1,1,1).subtract(new Point(1,1,1)),"ERROR: (point - itself) does not throw an exception");
    }

    @Test
    void distanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks the squared distance between point a and b
        assertEquals(9,new Point(1,2,3).distanceSquared(new Point(2,4,5)),"ERROR: squared distance between points is wrong");
        // TC02: Checks the squared distance between point b and a
        assertEquals(9,new Point(2,4,5).distanceSquared(new Point(1,2,3)),"ERROR: squared distance between points is wrong");
        // =============== Boundary Values Tests ==================
        // TC10: Checks the squared distance between a point and itself
        assertEquals(0,new Point(1,2,3).distanceSquared(new Point(1,2,3)),"ERROR: point squared distance to itself is not zero");
    }
    @Test
    void distance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks the distance between point a and b
        assertEquals(3,new Point(2,4,5).distance(new Point(1,2,3)),"ERROR: distance between points to itself is wrong");
        // TC02: Checks the distance between point b and a
        assertEquals(3,new Point(1,2,3).distance(new Point(2,4,5)),"ERROR: distance between points to itself is wrong");
        // =============== Boundary Values Tests ==================
        // TC10: Checks the distance between a point and itself
        assertEquals(0,new Point(1,2,3).distance(new Point(1,2,3)),"ERROR: point distance to itself is not zero");
    }
}