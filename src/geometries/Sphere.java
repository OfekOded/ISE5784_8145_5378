package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry{

    /** A variable that keeps the center of the circle */
    final private Point center;
    /**A constructor that initializes the radius and center values of the circle*/
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point p) {
        return p.subtract(this.center).normalize();
    }
}
