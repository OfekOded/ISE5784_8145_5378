package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    void testFindIntersections() {
        Point Vertex1= new Point(-6,0,0);
        Point Vertex2= new Point(-2,0,0);
        Point Vertex3= new Point(-4,-3,0);
        Point head=new Point(0,0,4);
        Triangle triangle=new Triangle(Vertex1,Vertex2,Vertex3);
        //TC01: The point inside the triangle
        final var result1=triangle.findIntersections(new Ray(head,new Vector(-4,-1,-4))).stream().toList();
        assertEquals(1,result1.size(),"ERROR: when the point inside the triangle");
        assertEquals(List.of(new Point(-4,-1,0)),result1,"ERROR: when the point inside the triangle");
        //TC02: The point outside the triangle opposite a side
        assertNull(triangle.findIntersections(new Ray(head,new Vector(-4,1,-4))),"ERROR: when the point outside the triangle opposite a side");
        //TC03: The point outside the triangle opposite a vertex
        assertNull(triangle.findIntersections(new Ray(head,new Vector(-4,-4,-4))),"ERROR: when the point outside the triangle opposite a vertex");
        //TC11: The point on the side
        final var result2=triangle.findIntersections(new Ray(head,new Vector(-4,0,-4))).stream().toList();
        assertEquals(1,result2.size(),"ERROR: when the point on the side");
        assertEquals(List.of(new Point(-4,0,0)),result2,"ERROR: when the point on the side");
        //TC12: The point is on a vertex
        final var result3=triangle.findIntersections(new Ray(head,new Vector(-4,-3,-4))).stream().toList();
        assertEquals(1,result3.size(),"ERROR: when the point is on a vertex");
        assertEquals(List.of(Vertex3),result3,"ERROR: when the point is on a vertex");
        //TC13: The point on the continuation of the side
        assertNull(triangle.findIntersections(new Ray(head,new Vector(-3,-4.5,-4))),"ERROR: when the point on the continuation of the side");
    }
}