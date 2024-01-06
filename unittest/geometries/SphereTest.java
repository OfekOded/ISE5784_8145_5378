package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    @Test
    void getNormal() {
        Sphere testSphere = new Sphere(2, new Point(0, 0, 0));
        assertEquals(new Vector(0, 1, 0), testSphere.getNormal(new Point(0, 1, 0)), "Sphere getNormal is not working");
    }
}