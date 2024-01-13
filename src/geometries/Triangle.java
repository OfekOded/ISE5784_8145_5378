package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Represents a Triangle in a three-dimensional space.
 * Extends the Polygon class with three vertices forming a triangular shape.
 */
public class Triangle extends Polygon {

    /**
     * Constructs a Triangle with three specified vertices.
     *
     * @param vertex1 The first vertex of the triangle.
     * @param vertex2 The second vertex of the triangle.
     * @param vertex3 The third vertex of the triangle.
     */
    public Triangle(Point vertex1, Point vertex2, Point vertex3) {
        super(vertex1, vertex2, vertex3);
    }

    /**
     * Finds the intersection points between the given ray and the triangle.
     *
     * @param ray The ray to find intersections with.
     * @return A list of intersection points, or null if there are no intersections.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        // If the ray doesn't intersect the plane of the triangle, return null
        if (plane.findIntersections(ray) == null)
            return null;

        // Calculate vectors from the ray's head to each vertex of the triangle
        Vector v1 = vertices.getFirst().subtract(ray.getHead());
        Vector v2 = vertices.get(1).subtract(ray.getHead());
        Vector v3 = vertices.getLast().subtract(ray.getHead());

        // Calculate normals of the three triangles formed by the ray's head and each pair of vertices
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        // Check if the ray's direction is within the half-spaces defined by the normals
        if (((ray.getDirection().dotProduct(n1) > 0) && (ray.getDirection().dotProduct(n2) > 0) && (ray.getDirection().dotProduct(n3) > 0))
                || ((ray.getDirection().dotProduct(n1) < 0) && (ray.getDirection().dotProduct(n2) < 0) && (ray.getDirection().dotProduct(n3) < 0))) {
            // If true, return the intersection points with the plane
            return plane.findIntersections(ray);
        }

        // If the ray's direction is not within the half-spaces, return null
        return null;
    }
}
