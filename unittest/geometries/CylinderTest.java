package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A set of test cases for the Cylinder class, focusing on the getNormal method.
 */
class CylinderTest {

    /**
     * Test case for the getNormal method of the Cylinder class.
     */
    @Test
    void testGetNormal() {
        // Create a test cylinder with radius 3, axis along z-axis, and height 8
        Cylinder cylinder = new Cylinder(3, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 8);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Point (1, 1, 8) on the surface of the cylinder
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(1, 1, 8)),
                "An error in calculating the normal for the upper base of the cylinder");

        // TC02: Point (1, 1, 0) on the surface of the cylinder
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(1, 1, 0)),
                "An error in calculating the normal for the lower base of the cylinder");

        // TC03: Point (0, 3, 7) on the surface of the cylinder
        assertEquals(new Vector(0, 1, 0), cylinder.getNormal(new Point(0, 3, 7)),
                "An error in calculating the normal for a point on the cylinder");
        // =============== Boundary Values Tests ==================
        // TC10: Point (0, 0, 0) at the base of the cylinder
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(0, 0, 0)),
                "Incorrect normal vector at the base of the cylinder when the point merges with the axis of the cylinder");
        // TC20: Point (0, 0, 8) at the second base of the cylinder
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(0, 0, 8)),
                "Incorrect normal vector at the base of the cylinder when the point merges with the axis of the cylinder");
    }

}
