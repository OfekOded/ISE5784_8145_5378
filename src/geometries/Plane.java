/**
 * The Plane class represents a flat surface in three-dimensional space.
 * It is defined either by providing a reference point and a normal vector, or by specifying three non-collinear points.
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * The Plane class represents a flat surface in three-dimensional space.
 */
public class Plane extends Geometry {

    /**
     * A reference point located within the plane.
     */
    final private Point referencePoint;

    /**
     * A vector perpendicular to the plane's surface (normal vector).
     */
    final private Vector normal;

    /**
     * Constructs a Plane object using a given reference point and normal vector.
     *
     * @param referencePoint The reference point within the plane.
     * @param normal         The normal vector perpendicular to the plane's surface.
     */
    public Plane(Point referencePoint, Vector normal) {
        this.referencePoint = referencePoint;
        this.normal = normal.normalize();
    }

    /**
     * Constructs a Plane object using three points.
     *
     * @param d1 The first point.
     * @param d2 The second point.
     * @param d3 The third point.
     * @throws IllegalArgumentException if the points are points converge or less than three points are provided.
     */
    public Plane(Point d1, Point d2, Point d3) {
        if (d1.equals(d2) || d1.equals(d3) || d2.equals(d3))
            throw new IllegalArgumentException("There are less than 3 points");
        Vector v1 = d1.subtract(d2).normalize();
        Vector v2 = d3.subtract(d2).normalize();
        if (v1.equals(v2) || v1.equals(v2.scale(-1)))
            throw new IllegalArgumentException("The three points on the same line cannot be made into a plane");
        this.referencePoint = d1;
        this.normal = v1.crossProduct(v2).normalize();
    }

    /**
     * Retrieves the normal vector of the plane.
     *
     * @return The normal vector of the plane.
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * Since all the normals of all the points in the plane are the same normal,
     * there is no point in calculating the normal at the point.
     * We only created this function because it is a technical matter that must override it
     *
     * @param point The point on the surface for which the normal vector is to be calculated.
     * @return The normal vector at the specified point.
     */
    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * Finds the intersection points between the given ray and the plane.
     * Returns a list of intersection points or null if there are no intersections.
     *
     * @param ray The ray for which intersections are to be found.
     * @return A list of intersection points with the plane or null if there are no intersections.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        // Check if the ray is parallel or almost parallel to the plane
        if (isZero(normal.dotProduct(ray.getDirection())))
            return null;

        // Calculate the parameter 't' for the intersection point using the plane equation
        double t = normal.dotProduct(referencePoint.subtract(ray.getHead())) / normal.dotProduct(ray.getDirection());

        // Check if the intersection point is behind the ray or at the origin
        if (t < 0 || isZero(t))
            return null;

        // Return a list containing the intersection point
        return List.of(new GeoPoint(this,ray.getPoint(t)));
    }

}
