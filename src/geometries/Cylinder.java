package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * The Cylinder class represents a cylindrical geometric shape that extends from Tube.
 * It is a subclass of the Tube class and includes information about its radius, axis, and height.
 */
public class Cylinder extends Tube {

    /**
     * The height of the cylinder.
     */
    final private double height;

    /**
     * Constructs a Cylinder with the specified radius, axis, and height.
     *
     * @param radius The radius of the cylinder.
     * @param axis   The central axis of the cylinder.
     * @param height The height of the cylinder.
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }

    /**
     * Computes and returns the normal vector at a given point on the surface of the cylinder.
     * Overrides the getNormal method in the Tube class.
     *
     * @param p The point on the cylinder's surface for which to compute the normal vector.
     * @return The normal vector at the specified point.
     */
    @Override
    public Vector getNormal(Point p) {
        // Check if the point is at either end of the cylinder
        if (p.equals(axis.getHead()) || p.equals(axis.getHead().add(axis.getDirection().scale(height))))
            return axis.getDirection();

        // Calculate vector v1 from the cylinder's axis head to the given point
        Vector v1 = p.subtract(axis.getHead());

        // Check if the point is on the axis (parallel to the direction vector)
        if (Util.isZero(v1.dotProduct(axis.getDirection())))
            return axis.getDirection();

        // Calculate the parameter 't' representing the projection of the point onto the axis
        double t = v1.dotProduct(axis.getDirection());

        // Check if the point is at the top end of the cylinder
        if (t == height)
            return axis.getDirection();

        // Calculate the point 'o' on the axis corresponding to the projection 't'
        Point o = axis.getHead().add(axis.getDirection().scale(t));

        // Calculate and return the normalized vector from point 'o' to the given point
        return p.subtract(o).normalize();
    }
}