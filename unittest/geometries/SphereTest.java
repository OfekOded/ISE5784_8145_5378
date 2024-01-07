/**
 * A set of test cases for the Sphere class, focusing on the getNormal method.
 */
package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A set of test cases for the Sphere class, focusing on the getNormal method.
 */
class SphereTest {

    /**
     * Test case: Checking the normal vector at a specific point on the surface of the sphere.
     */
    @Test
    void getNormal() {
        // Create a test sphere with radius 2 and center at the origin
        Sphere testSphere = new Sphere(2, new Point(0, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checking the normal vector at the point (0, 2, 0) on the surface of the sphere
        assertEquals(new Vector(0, 1, 0), testSphere.getNormal(new Point(0, 2, 0)),
                "Error in normal calculation for a sphere");
    }
}
