package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    @Test
    void getNormal() {
        assertEquals(new Vector(0, 0, 1), new Cylinder(2, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 8), "f");
        assertEquals(new Vector(0, 0, -1), new Cylinder(2, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 8), "f");
    }
}