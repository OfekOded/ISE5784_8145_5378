/**
 * A set of test cases for the Point class, covering various methods such as add, subtract, distanceSquared, and distance.
 */
package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A set of test cases for the Point class, covering various methods such as add, subtract, distanceSquared, and distance.
 */
class PointTests {

    Point point1 = new Point(1, 2, 3);
    Point point2 = new Point(2, 4, 5);
    Point point3 = new Point(2, 4, 6);
    /**
     * Test case for the add method of the Point class.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks the integrity of add function
        assertEquals(point3,point1.add(new Vector(1, 2, 3)),
                     "ERROR: (point + vector) = other point does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC10: Checks in case the result is the zero vector
        assertEquals(new Point(0, 0, 0),point1.add(new Vector(-1, -2, -3)),
                     "ERROR: (point + vector) = center of coordinates does not work correctly");
    }

    /**
     * Test case for the subtract method of the Point class.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks the
        // integrity of subtract points
        assertEquals(new Vector(1, 2, 3), point3.subtract(point1),
                     "ERROR: (point2 - point1) does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC10: Checks in case the result is the zero vector
        assertThrows(IllegalArgumentException.class, () -> new Point(1, 1, 1).subtract(new Point(1, 1, 1)),
                     "ERROR: (point - itself) does not throw an exception");
    }

    /**
     * Test case for the distanceSquared method of the Point class.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks the squared distance between point a and b
        assertEquals(9,point1.distanceSquared(point2),
                     "ERROR: squared distance between points is wrong");

        // TC02: Checks the squared distance between point b and a
        assertEquals(9, point2.distanceSquared(point1),
                     "ERROR: squared distance between points is wrong");

        // =============== Boundary Values Tests ==================
        // TC10: Checks the squared distance between a point and itself
        assertEquals(0,point1.distanceSquared(point1),
                     "ERROR: point squared distance to itself is not zero");
    }

    /**
     * Test case for the distance method of the Point class.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks the distance
        // e between point a and b
        assertEquals(3, point2.distance(point1),
                     "ERROR: distance between points to itself is wrong");

        // TC02: Checks the distance between point b and a
        assertEquals(3,point1.distance(point2),
                     "ERROR: distance between points to itself is wrong");

        // =============== Boundary Values Tests ==================
        // TC10: Checks the distance between a point and itself
        assertEquals(0,point1.distance(point1),
                     "ERROR: point distance to itself is not zero");
    }
}