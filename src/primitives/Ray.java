package primitives;

import geometries.Intersectable.GeoPoint;
import java.util.List;
import java.util.Objects;
import static primitives.Util.isZero;

/**
 * The Ray class represents a geometric ray in three-dimensional space.
 * It is used in more complex geometric shapes such as cylinders, etc.
 */
public class Ray {
    private static final double DELTA = 0.1;
    /**
     * The variable stores a point representing the head of the ray.
     */
    final private Point head;

    /**
     * The variable stores the direction of the ray the variable is of vector type.
     */
    final private Vector direction;

    /**
     * Constructs a new Ray object with the given head point and direction vector.
     * The direction vector is normalized during construction.
     *
     * @param head The head point of the ray.
     * @param direction The direction vector of the ray.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }
    /**
     * Initializes a ray and makes a correction because of the inaccuracy in the calculations
     *
     * @param point The starting point of the ray.
     * @param v The direction vector of the ray.
     * @param n The normal vector.
     */
    public Ray(Point point, Vector v, Vector n) {
        double nv = v.dotProduct(n);
        this.head = (isZero(nv) ? point : point.add(n.scale(nv < 0 ? -DELTA : DELTA)));
        this.direction = v.normalize();
    }
    /**
     * Retrieves the head point of the ray.
     *
     * @return The head point of the ray.
     */
    public Point getHead() {
        return head;
    }

    /**
     * Retrieves the direction vector of the ray.
     *
     * @return The direction vector of the ray.
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Calculates and returns a point along the ray at a given parameter value 't'.
     *
     * @param t The parameter value along the ray.
     * @return A point along the ray at the specified parameter value.
     */
    public Point getPoint(double t) {
        if (isZero(t))
            return head;
        return head.add(direction.scale(t));
    }

    /**
     * Finds and returns the closest point from a list of points to the ray's head.
     *
     * @param points The list of points to find the closest one.
     * @return The closest point to the ray's head.
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Finds and returns the closest GeoPoint from a list of GeoPoints to the ray's head.
     *
     * @param intersections The list of GeoPoints to find the closest one.
     * @return The closest GeoPoint to the ray's head.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
        if (intersections==null)
            return null;
        GeoPoint closestGeoPoint =intersections.get(0);
        for (GeoPoint geoPoint : intersections) {
            if (geoPoint.point.distance(head) < closestGeoPoint.point.distance(head))
                    closestGeoPoint = geoPoint;
            }
        return closestGeoPoint;
    }
    /**
     * Compares this ray with the specified object for equality.
     * Two rays are considered equal if they have the same head and direction.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return Objects.equals(head, ray.head) && Objects.equals(direction, ray.direction);
    }
    /**
     * Returns a string representation of the ray.
     * The string includes the head and direction attributes.
     *
     * @return a string representation of the ray
     */
    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }
}
