/**
 * The Tube class represents a three-dimensional tube-like geometry.
 * It extends the RadialGeometry abstract class and implements the Geometry interface.
 */
package geometries;

import primitives.*;

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
     * @param p The point on the surface for which the normal vector is to be calculated.
     * @return The normal vector at the specified point.
     */
    @Override
    public Vector getNormal(Point p) {
        // Calculate vector v1 from the tube's axis head to the given point
        Vector v1 = p.subtract(axis.getHead());

        // Calculate the parameter 't' representing the projection of the point onto the axis
        double t = v1.dotProduct(axis.getDirection());

        // Check if the point is on the axis (parallel to the direction vector)
        if (Util.isZero(t))
            return v1.normalize();

        // Calculate the point 'o' on the axis corresponding to the projection 't'
        Point o = axis.getHead().add(axis.getDirection().scale(t));

        // Calculate and return the normalized vector from point 'o' to the given point
        return p.subtract(o).normalize();
    }
}
