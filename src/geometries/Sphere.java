/**
 * The Sphere class represents a three-dimensional spherical geometry.
 * It extends the RadialGeometry abstract class and implements the Geometry interface.
 */
package geometries;

import primitives.Point;
import primitives.Vector;

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
     * @param p The point on the surface for which the normal vector is to be calculated.
     * @return The normal vector at the specified point.
     */
    @Override
    public Vector getNormal(Point p) {
        return p.subtract(this.center).normalize();
    }
}
