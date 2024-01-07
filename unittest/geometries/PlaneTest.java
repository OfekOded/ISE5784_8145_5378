package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A set of test cases for the Plane class.
 */
class PlaneTest {

    @Test
    void getNormal() {
        Point p1 = new Point(1, 0, 0);
        Point p2 = new Point(0, 1, 0);
        Point p3 = new Point(0, 0, 0);
        Plane testPlane = new Plane(p1, p2, p3);
        // ============ Equivalence Partitions Tests ==============
        //TC01: Checking the normal vector of the constructed plane.
        assertEquals(new Vector(0, 0, 1), testPlane.getNormal(p3), "Incorrect normal vector");
    }

    @Test
    void constructor() {
        // =============== Boundary Values Tests ==================
        //TC01: Attempting to create a plane with two points converge, expecting an IllegalArgumentException.
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0)),
                "Failed to throw exception for three collinear points");
        //TC02: Attempting to create a plane with three points on the same line, expecting an IllegalArgumentException.
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1, 0, 0), new Point(2, 0, 0), new Point(3, 0, 0)),
                "Failed to throw exception for three points on the same line");
    }
}
