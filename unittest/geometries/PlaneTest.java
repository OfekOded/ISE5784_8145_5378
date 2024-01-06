package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
    void getNormal() {
        Point p1 = new Point(1,0,0);
        Point p2 = new Point(0,1,0);
        Point p3 = new Point(0,0,0);
        Plane testPlane = new Plane(p1,p2,p3);
        assertEquals(new Vector(0,0,1),testPlane.getNormal(p3),"c");
        assertThrows(IllegalArgumentException.class,()->new Plane(p1,p1,p2),"g");
        assertThrows(IllegalArgumentException.class,()->new Plane(p1,new Point(2,0,0),new Point(3,0,0)),"g");
    }

    @Test
    void constructor() {

    }
}