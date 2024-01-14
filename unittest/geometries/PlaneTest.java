package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A set of test cases for the Plane class.
 */
class PlaneTest {
    Point point1 = new Point(1, 0, 0);
    Point point2 = new Point(0, 1, 0);
    Point point5 = new Point(0, 0, 0);
    @Test
    void getNormal() {

        Plane testPlane = new Plane(point1, point2, point3);
        // ============ Equivalence Partitions Tests ==============
        //TC01: Checking the normal vector of the constructed plane.
        assertEquals(new Vector(0, 0, 1), testPlane.getNormal(point3), "Incorrect normal vector");
    }

    @Test
    void testConstructor() {
        // =============== Boundary Values Tests ==================
        //TC01: Attempting to create a plane with two points converge, expecting an IllegalArgumentException.
        assertThrows(IllegalArgumentException.class, () -> new Plane(point1, point1, point2),
                "Failed to throw exception for three collinear points");
        //TC02: Attempting to create a plane with three points on the same line, expecting an IllegalArgumentException.
        assertThrows(IllegalArgumentException.class, () -> new Plane(point1, new Point(2, 0, 0), new Point(3, 0, 0)),
                "Failed to throw exception for three points on the same line");
    }

    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Point(1, 0, 1), new Point(0, 0, 1), new Point(0, 1, 1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: The ray cuts the plane
        final var result1 = plane.findIntersections(new Ray(new Point(0, 0, 4), new Vector(-4, 0, -4))).stream().toList();
        assertEquals(1, result1.size(), "ERROR: when the ray cuts the plane");
        assertEquals(List.of(new Point(-3, 0, 1)), result1, "ERROR: when the ray cuts the plane");

        // TC02: The ray doesn't cut the plane
        assertNull(plane.findIntersections(new Ray(new Point(0, 0, 2), new Vector(3.4, -3, 1.5))), "ERROR: when the ray doesn't cut the plane");

        // =============== Boundary Values Tests ==================
        //TC11: The ray is parallel to the plane
        assertNull(plane.findIntersections(new Ray(new Point(0, 0, 2), new Vector(0, 1, 0))), "ERROR: when the ray is parallel to the plane");

        //TC12: The ray merges with the plane
        assertNull(plane.findIntersections(new Ray(new Point(0, 0, 1), new Vector(0, 1, 0))), "ERROR: when the ray merges with the plane");

        //TC13: The ray is perpendicular and starts before the plane
        final var result2 = plane.findIntersections(new Ray(new Point(1, 1, 0), new Vector(0, 0, 1))).stream().toList();
        assertEquals(1, result2.size(), "ERROR: when the ray is perpendicular and starts before the plane");
        assertEquals(List.of(new Point(1, 1, 1)), result2, "ERROR: when the ray is perpendicular and starts before the plane");

        //TC13: The ray is perpendicular and starts at the plane
        assertNull(plane.findIntersections(new Ray(new Point(1, 1, 1), new Vector(0, 0, 1))), "ERROR: when the ray is perpendicular and starts at the plane");

        //TC13: The ray is perpendicular and starts after the plane
        assertNull(plane.findIntersections(new Ray(new Point(1, 1, 2), new Vector(0, 0, 1))), "ERROR: when the ray is perpendicular and starts after the plane");

        //TC14: The ray starts in a plane (not perpendicular)
        assertNull(plane.findIntersections(new Ray(new Point(0, 0, 1), new Vector(-2, 0, 1))), "ERROR: when the ray starts in a plane (not perpendicular)");
    }
}
