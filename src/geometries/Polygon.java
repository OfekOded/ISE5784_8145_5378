package geometries;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * @author Dan
 */
public class Polygon extends Geometry {
    /** List of polygon's vertices */
    protected final List<Point> vertices;
    /** Associated plane in which the polygon lays */
    protected final Plane       plane;
    /** The size of the polygon - the amount of the vertices in the polygon */
    private final int           size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     * @param  vertices                 list of vertices according to their order by
     *                                  edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        size          = vertices.length;

        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane         = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) return; // no need for more tests for a Triangle

        Vector  n        = plane.getNormal();
        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector  edge1    = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector  edge2    = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with the normal. If all the rest consequent edges will generate the same sign
        // - the polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }

    }

    /**
     * Gets the normal vector of the convex polygon at a given point. Since the convex polygon lies on a plane,
     * the normal vector is the same as the normal vector of the plane containing the polygon.
     *
     * @param point The point on the convex polygon for which the normal vector is calculated.
     * @return The normal vector of the convex polygon at the specified point.
     */
    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }

    /**
     * Finds the intersection points between the polygon and a given ray which are at a maximum distance from the head of the beam.
     * @param ray The ray for which intersections are to be found.
     * @param maxDistance
     * @return A list of GeoPoint objects representing the intersection points and the polygon.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance){
        // Find intersections with the plane containing the polygon.
        List<Point> intersectionPoint = plane.findIntersections(ray,maxDistance);

        // If there are no intersections with the plane, return null.
        if (intersectionPoint == null)
            return null;

        // Create a list to store vectors from the ray's head to each vertex of the polygon.
        List<Vector> vectorsList = new LinkedList<>();
        for (Point vertex : vertices)
            vectorsList.add(vertex.subtract(ray.getHead()));

        // Create a list to store normals of the edges formed by consecutive vectors.
        List<Vector> normals = new LinkedList<>();
        for (int i = 0; i < vectorsList.size(); i++)
            normals.add(vectorsList.get(i).crossProduct(vectorsList.get((i + 1) % vectorsList.size())));

        // Initialize a flag to check if all normals have the same sign with respect to the ray's direction.
        boolean flag = true;

        // Check the sign consistency of the dot products between each normal and the ray's direction.
        for (int i = 0; i < normals.size(); i++)
            if (!Util.compareSign(normals.get(i).dotProduct(ray.getDirection()), normals.get((i + 1) % normals.size()).dotProduct(ray.getDirection())))
                flag = false;

        // If all dot products have the same sign, return the intersection points.
        if (flag)
            return List.of(new GeoPoint(this,intersectionPoint.getFirst()));

        // If the signs are inconsistent, return null.
        return null;
    }

}
