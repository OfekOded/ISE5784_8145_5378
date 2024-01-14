/**
 * The Tube class represents a three-dimensional tube-like geometry.
 * It extends the RadialGeometry abstract class and implements the Geometry interface.
 */
package geometries;

import primitives.*;

import java.util.List;

/**
 * The Tube class represents a three-dimensional tube-like geometry.
 * It extends the RadialGeometry abstract class and implements the Geometry interface.
 */
public class Tube extends RadialGeometry {

    /**
     * The axis of the tube, represented by a Ray.
     */
    final protected Ray axis;

    /**
     * Constructs a Tube object with the specified radius and axis.
     *
     * @param radius The radius of the tube.
     * @param axis   The central axis of the tube, represented by a Ray.
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    /**
     * Calculates and returns the normal vector at a specified point on the surface of the tube.
     *
     * @param point The point on the surface for which the normal vector is to be calculated.
     * @return The normal vector at the specified point.
     */
    @Override
    public Vector getNormal(Point point) {
        // Calculate vector v1 from the tube's axis head to the given point
        Vector v1 = point.subtract(axis.getHead());

        // Calculate the parameter 't' representing the projection of the point onto the axis
        double t = v1.dotProduct(axis.getDirection());

        // Check if the point is on the axis (parallel to the direction vector)
        if (Util.isZero(t))
            return v1.normalize();

        // Calculate the point 'o' on the axis corresponding to the projection 't'
        Point o = axis.getPoint(t);
        // Calculate and return the normalized vector from point 'o' to the given point
        return point.subtract(o).normalize();
    }

    /**
     * This method is overridden from the Intersectable interface.
     * It returns null, indicating that there are no intersection points
     * between the given ray and the object implementing this method.
     *
     * @param ray The ray to find intersections with.
     * @return Always returns null since this object does not have intersections with the given ray.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }

}
