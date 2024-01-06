package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Cylinder extends Tube {
    final private double height;

    /**
     * A constructor that initializes the values of a cylinder by using the constructor of tube and initializing the height
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {
        if (p.equals(axis.getHead()) || p.equals(axis.getHead().add(axis.getDirection().scale(height))))
            return axis.getDirection();
        Vector v1 = p.subtract(axis.getHead());
        if (Util.isZero(v1.dotProduct(axis.getDirection())))
            return axis.getDirection();
        double t = v1.dotProduct(axis.getDirection());
        if (t == height)
            return axis.getDirection();
        Point o = axis.getHead().add(axis.getDirection().scale(t));
        return p.subtract(o).normalize();

    }
}
