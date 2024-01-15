/**
 * A set of test cases for the Tube class, focusing on the getNormal method.
 */
package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A set of test cases for the Tube class, focusing on the getNormal method.
 */
class TubeTest {

    /**
     * Test case: Checking the normal vector at specific points on the surface of the tube.
     */
    @Test
    void testGetNormal() {
        // Create a test tube with radius 2 and axis along the x-axis
        Tube testTube = new Tube(2, new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checking the normal vector at the point (2, 0, 2) on the surface of the tube
        assertEquals(new Vector(0, 0, 1), testTube.getNormal(new Point(2, 0, 2)),
                "An error in calculating the normal on the surface of the tube");
        // =============== Boundary Values Tests ==================
        // TC10: Checking the normal vector at the point (0, 0, 2) on the surface of the tube
        assertEquals(new Vector(0, 0, 1), testTube.getNormal(new Point(0, 0, 2)),
                "An error in calculating the normal on the surface of the tube when the point is in front of the head of the ray");
    }
}
