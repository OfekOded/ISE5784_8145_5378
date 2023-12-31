package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{
    final private double height;
    /**A constructor that initializes the values of a cylinder by using the constructor of tube and initializing the height*/
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {
        return super.getNormal(p);
    }
}
