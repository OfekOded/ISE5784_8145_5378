/**
 * The Sphere class represents a three-dimensional spherical geometry.
 * It extends the RadialGeometry abstract class and implements the Geometry interface.
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * The Sphere class represents a three-dimensional spherical geometry.
 * It extends the RadialGeometry abstract class and implements the Geometry interface.
 */
public class Sphere extends RadialGeometry {

    /**
     * The center point of the sphere.
     */
    final private Point center;

    /**
     * Constructs a Sphere object with the specified radius and center.
     *
     * @param radius The radius of the sphere.
     * @param center The center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Calculates and returns the normal vector at a specified point on the surface of the sphere.
     *
     * @param point The point on the surface for which the normal vector is to be calculated.
     * @return The normal vector at the specified point.
     */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(this.center).normalize();
    }

    /**
     * Finds the intersection points between the given ray and the sphere.
     * Returns a list of intersection points or null if there are no intersections.
     *
     * @param ray The ray for which intersections are to be found.
     * @return A list of intersection points with the sphere or null if there are no intersections.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        // Special case: Ray starts from the center of the sphere
        if (center.equals(ray.getHead()))
            return List.of(ray.getPoint(radius));

        // Calculate vector 'u' connecting the center of the sphere to the camera
        Vector u = center.subtract(ray.getHead());

        // Calculate projection of 'u' onto the ray direction
        double tm = u.dotProduct(ray.getDirection());

        // Calculate distance from the center to the ray (d)
        double d = Math.sqrt(u.lengthSquared() - tm * tm);

        // Check for no intersection or tangential intersection
        if (d > radius || isZero(d - radius))
            return null;

        // Calculate half-chord length (th) using Pythagorean theorem
        double th = Math.sqrt(radius * radius - d * d);

        // Calculate intersection parameters
        double t1 = tm - th;
        double t2 = tm + th;

        // Check for valid intersection points
        if ((t1 < 0 || isZero(t1)) && t2 > 0)
            return List.of(ray.getPoint(t2));
        else if (t2 > 0)
            return List.of(ray.getPoint(t1), ray.getPoint(t2));

        // No valid intersection points
        return null;
    }
}
