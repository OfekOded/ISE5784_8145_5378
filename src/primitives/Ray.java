package primitives;
import geometries.Intersectable.GeoPoint;
import geometries.Intersectable.GeoPoint;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static primitives.Util.isZero;

/**
 * The Ray class represents a geometric ray in three-dimensional space.
 * It is used in more complex geometric shapes such as cylinders, etc.
 */
public class Ray {
    /**
     * The variable stores a point representing the head of the ray.
     */
    final private Point head;

    /**
     * The variable stores the direction of the ray, the variable is of vector type.
     */
    final private Vector direction;

    /**
     * Constructs a new Ray object with the given head point and direction vector.
     * The direction vector is normalized during construction.
     *
     * @param point The head point of the ray.
     * @param vector The direction vector of the ray.
     */
    public Ray(Point point, Vector vector) {
        this.head = point;
        this.direction = vector.normalize();
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
        if(isZero(t))
            return head;
        return head.add(direction.scale(t));
    }

    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }


    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections){
        GeoPoint closestGeoPoint=intersections.getFirst();
        for(GeoPoint geoPoint :intersections){
            if(geoPoint.point.distance(head)<closestGeoPoint.point.distance(head))
                closestGeoPoint=geoPoint;
        }
        return closestGeoPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Ray ray)) return false;
        return Objects.equals(head, ray.head) && Objects.equals(direction, ray.direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }
}
