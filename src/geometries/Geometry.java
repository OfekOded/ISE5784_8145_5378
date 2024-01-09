/**
 * The Geometry interface serves as an abstraction for all geometric shapes in the application.
 * Any class implementing this interface is expected to provide a method for computing the normal vector
 * at a given point on the surface of the shape.
 */
package geometries;

import primitives.Vector;
import primitives.Point;

/**
 * The Geometry interface represents a geometric shape in the application.
 */
public interface Geometry extends Intersectable {

    /**
     * Calculates and returns the normal vector at the specified point on the surface of the geometry.
     *
     * @param p The point on the surface for which the normal vector is to be calculated.
     * @return The normal vector at the specified point.
     */
    Vector getNormal(Point p);
}
