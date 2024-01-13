package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {
    @Test
    void findIntersections() {
        Plane plane=new Plane(new Point(4,0,0),new Point(4,0,1),new Point(4,1,1));
        Sphere sphere=new Sphere(1,new Point(2,0,0));
        Triangle triangle=new Triangle(new Point(6,0,0),new Point(6,5,0),new Point(6,3,5));
        Geometries geometries=new Geometries(sphere,plane,triangle);
        //TC11: An empty body collection
        Geometries geometries11=new Geometries();
        assertNull(geometries11.findIntersections(new Ray(new Point(0,0,2),new Vector(-1,0,0))),"Error in case empty body collection");
        //TC12: No shape is cut
        assertNull(geometries.findIntersections(new Ray(new Point(0,0,2),new Vector(-1,0,0))),"Error in case no shape is cut");
        //TC13: Only one shape is cut
        assertEquals(1,geometries.findIntersections(new Ray(new Point(0,-4,0),new Vector(4,0,0))).size(),"Error in case only one shape is cut");
        //TC01: Some shapes (but not all) are cut
        assertEquals(2,geometries.findIntersections(new Ray(new Point(0,3,3),new Vector(2,0,0))).size(),"Error in case some shapes (but not all) are cut");
        //TC14: All shapes are cut
        assertEquals(4,geometries.findIntersections(new Ray(new Point(1,-1,0),new Vector(3,2.5,1))).size(),"Error in case all shapes are cut");
    }
}