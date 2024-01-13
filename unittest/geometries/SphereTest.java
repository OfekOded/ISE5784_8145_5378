/**
 * A set of test cases for the Sphere class, focusing on the getNormal method.
 */
package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


import java.util.Comparator;
import java.util.List;
import java.util.function.ToDoubleFunction;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A set of test cases for the Sphere class, focusing on the getNormal method.
 */
class SphereTest {
    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);

    /**
     * Test case: Checking the normal vector at a specific point on the surface of the sphere.
     */
    @Test
    void testGetNormal() {
        // Create a test sphere with radius 2 and center at the origin
        Sphere testSphere = new Sphere(2, new Point(0, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checking the normal vector at the point (0, 2, 0) on the surface of the sphere
        assertEquals(new Vector(0, 1, 0), testSphere.getNormal(new Point(0, 2, 0)),
                "Error in normal calculation for a sphere");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, p100);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310));
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        final var result3 = sphere.findIntersections(new Ray(new Point(0.41,-0.2,0.08), new Vector(0.96, -0.42, 0.62))).stream().toList();
        assertEquals(1, result3.size(), "ERROR: when ray starts inside the sphere");
        assertEquals(List.of(new Point(1.3648125221938283,-0.6177304784597999,0.6966497539168474)),result3,"ERROR: when ray starts inside the sphere");
        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2.25,0.48,-0.22), new Vector(0.87,0.26,0.22))), "ERROR: when ray starts after the sphere");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        final var result11 = sphere.findIntersections(new Ray(new Point(0.82,-0.05,0.98), new Vector(-0.19,-0.87,-0.98))).stream().toList();
        assertEquals(1, result11.size(), "ERROR: when ray starts at sphere and goes inside (but not through the center)");
        assertEquals(List.of(new Point(0.6281951653374307,-0.9282642429286064,-0.009309147206935764)), result11, "ERROR: when ray starts at sphere and goes inside (but not through the center)");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1.52,-0.86,0), new Vector(0.98,-0.14,0))), "ERROR: when ray starts at sphere and goes outside (but not through the center)");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        final var result13 = sphere.findIntersections(new Ray(new Point(1,-2,0), new Vector(0,2,0))).stream().toList();
        assertEquals(2, result13.size(), "ERROR: when ray starts before the sphere (through the center)");
        assertEquals(List.of(new Point(1,-1,0), new Point(1,1,0)), result13, "ERROR: when ray starts before the sphere (through the center)");

        // TC14: Ray starts at sphere and goes inside (1 point)
        final var result14 = sphere.findIntersections(new Ray(new Point(1,1,0), new Vector(0, -1, 0))).stream().toList();
        assertEquals(1, result14.size(), "ERROR: when ray starts at sphere and goes inside (through the center)");
        assertEquals(List.of(new Point(1,-1,0)), result14, "ERROR: when ray starts at sphere and goes inside (through the center)");

        // TC15: Ray starts inside (1 point)
        final var result15=sphere.findIntersections(new Ray(new Point(1,0.5,0), new Vector(0, 1, 0))).stream().toList();
        assertEquals(1, result15.size(), "ERROR: when ray starts inside (through the center)");
        assertEquals(List.of(new Point(1, 1, 0)), result15, "ERROR: when ray starts inside (through the center)");

        // TC16: Ray starts at the center (1 point)
        final var result16 =sphere.findIntersections(new Ray(new Point(1,0,0),new Vector(0,0,1))).stream().toList();
        assertEquals(1,result16.size(),"ERROR: when ray starts at the center (through the center)");
        assertEquals(List.of(new Point(1,0,1)),result16,"ERROR: when ray starts at the center (through the center)");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1,1,0),new Vector(0,1,0))),"ERROR: when ray starts at sphere and goes outside (through the center)");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1,3,0),new Vector(0,1,0))),"ERROR: when ray starts after sphere (through the center)");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,-1,0),new Vector(1,0,0))),"ERROR: when ray starts before the tangent point");
        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1,-1,0),new Vector(1,0,0))),"ERROR: when ray starts at the tangent point");
        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(3,-1,0),new Vector(1,0,0))),"ERROR: when ray starts after the tangent point");
        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(-1,0,0),new Vector(0,0,1))),"ERROR: when ray's line is outside, ray is orthogonal to ray start to sphere's center line");
    }
}
