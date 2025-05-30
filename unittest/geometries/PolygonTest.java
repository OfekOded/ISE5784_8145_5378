package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing Polygons
 *
 * @author Dan
 */

public class PolygonTest {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;
    Point point1 = new Point(1, 0, 0);
    Point point2 = new Point(0, 1, 0);
    Point point3 = new Point(0, 0, 1);
    Point point4 = new Point(-1, 1, 1);
    Point Vertex1 = new Point(1, 0, 0);

    Point Vertex2 = new Point(2, 0, 0);

    Point Vertex8 = new Point(0, 0, 1);

    Point Vertex7 = new Point(0, 0, 2);

    Point Vertex6 = new Point(1, 0, 3);

    Point Vertex5 = new Point(2, 0, 3);

    Point Vertex4 = new Point(3, 0, 2);

    Point Vertex3 = new Point(3, 0, 1);

    Point head = new Point(1, -2, 0);

    Polygon polygon = new Polygon(Vertex1, Vertex2, Vertex3, Vertex4, Vertex5, Vertex6, Vertex7, Vertex8);
    /**
     * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
     */
    @Test
    public void testConsructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        assertDoesNotThrow(() -> new Polygon(point3,
                        point1,
                        point2,
                        point4),
                "Failed constructing a correct polygon");

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(point3, point2, point1, point4), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(point3, point1, point2, new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(point3, point1, point2,
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(point3, point1, point2,
                        new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(point3, point1, point2, point3),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(point3, point1, point2, new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts =
                {point3, point1, point2, point4};
        Polygon pol = new Polygon(pts);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(point3), "");
        // generate the test result
        Vector result = pol.getNormal(point3);
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Polygon's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA,
                    "Polygon's normal is not orthogonal to one of the edges");
    }

    @Test
    void findIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: The point inside the polygon
        final var result1 = polygon.findIntersections(new Ray(head, new Vector(0, 2, 1))).stream().toList();
        assertEquals(1, result1.size(), "ERROR: when the point inside the polygon");
        assertEquals(List.of(new Point(1, 0, 1)), result1, "ERROR: when the point inside the polygon");
        //TC02: The point outside the polygon opposite a side
        assertNull(polygon.findIntersections(new Ray(head, new Vector(-4, 1, -4))), "ERROR: when the point outside the polygon opposite a side");

        //TC03: The point outside the polygon opposite a vertex
        assertNull(polygon.findIntersections(new Ray(head, new Vector(-4, -4, -4))), "ERROR: when the point outside the polygon opposite a vertex");

        // =============== Boundary Values Tests ==================
        //TC11: The point on the side
        assertNull(polygon.findIntersections(new Ray(head, new Vector(-4, 0, -4))),"ERROR: when the point on the side");

        //TC12: The point is on a vertex
        assertNull(polygon.findIntersections(new Ray(head, new Vector(-4, -3, -4))),"ERROR: when the point is on a vertex");

        //TC13: The point on the continuation of the side
        assertNull(polygon.findIntersections(new Ray(head, new Vector(-3, -4.5, -4))), "ERROR: when the point on the continuation of the side");
    }
    @Test
    void testFindMaxDistanceIntersections(){
        // ============ Equivalence Partitions Tests ==============
        //TC01: The distance between the intersection point and the polygon is less than maxDistance
        final var result1 = polygon.findIntersections(new Ray(head, new Vector(0, 2, 1)),100000);
        assertEquals(1, result1.size(), "ERROR: when the ray cuts the polygon and the distance between the intersection point and the plane is less than maxDistance");
        //TC02: The distance between the intersection point and the polygon is bigger than maxDistance
        assertNull(polygon.findIntersections(new Ray(head, new Vector(0, 2, 1)),0.5), "ERROR: when the ray cuts the polygon and the distance between the intersection point and the plane is bigger than maxDistance");
        // =============== Boundary Values Tests ==================
        // TC11: The distance between the intersection point and the polygon is maxDistance
        final var result2 = polygon.findIntersections(new Ray(head, new Vector(0, 2, 1)),2.236);
        assertEquals(1, result1.size(), "ERROR: when the ray cuts the polygon and the distance between the intersection point and the plane is maxDistance");
    }
}





