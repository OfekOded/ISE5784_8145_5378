package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    @Test
    void getNormal() {
        Cylinder testCylinder = new Cylinder(3, new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 8);
        assertEquals(new Vector(0, 0, 1), testCylinder.getNormal(new Point(1, 1, 8)), "f");
        assertEquals(new Vector(0, 0, -1), testCylinder.getNormal(new Point(1, 1, 0)), "f");
        assertEquals(new Vector(0, 1, 0), testCylinder.getNormal(new Point(0,3,7)), "f");
        assertEquals(new Vector(0, 0, 1), testCylinder.getNormal(new Point(0,0,0)), "f");

    }
}