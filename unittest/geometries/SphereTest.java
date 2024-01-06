package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    @Test
    void getNormal() {
        Sphere s=new Sphere(2,new Point(0,0,0));
        assertEquals(new Vector(0,1,0),s.getNormal(new Point(0,1,0)),"Sphere getNormal is not working");
    }
}