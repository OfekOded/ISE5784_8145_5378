package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void getPoint() {
        Ray ray = new Ray(new Point(1,0,0),new Vector(1,0,0));
        // ============ Equivalence Partitions Tests ==============
        //TC01: test when t is positive
        assertEquals(new Point(2,0,0),ray.getPoint(1),"Error in getPoint test when t is positive");
        //TC02: test when t is negative
        assertEquals(new Point(0,0,0),ray.getPoint(-1),"Error in getPoint test when t is negative");
        // =============== Boundary Values Tests ==================
        //TC01: test when t is zero
        assertEquals(new Point(1,0,0),ray.getPoint(0),"Error in getPoint test when t is zero");
    }

    @Test
    void findClosestPoint(){
        Point pointA=new Point(1,0,0);
        Point pointB=new Point(3,0,0);
        Point pointC=new Point(5,0,0);
        List<Point> list1=List.of(pointA,pointB,pointC);
        // ============ Equivalence Partitions Tests ==============
        Ray ray1=new Ray(new Point(3,4,0), new Vector(1.5,-4,0));
        assertEquals(pointB,ray1.findClosestPoint(list1),"ERROR: in case the point in the middle of the list is the one closest to the beginning of the ray");
        // =============== Boundary Values Tests ==================
        List<Point> list2=null;
        assertNull(ray1.findClosestPoint(list2), "ERROR: in case the list is empty");
        Ray ray2=new Ray(new Point(0,2,0),new Vector(2,-2,0));
        assertEquals(pointA,ray2.findClosestPoint(list1),"ERROR: in case the point at the beginning of the list is the one closest to the beginning of the ray");
        Ray ray3=new Ray(new Point(6,2,0),new Vector(-2,-2,0));
        assertEquals(pointC,ray3.findClosestPoint(list1),"ERROR: in case the point at the end of the list is the one closest to the beginning of the ray");
    }
}