/**
 * This class is used as a plane
 */
package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {

    /**
     * A variable that keeps a reference point inside the plane
     */

    final private Point referencePoint;

    /**
     * A variable that keeps the vector normal to the surface
     */

    final private Vector normal;

    /**
     * A constructor that accepts a normal vector and reference point and initializes the variables
     */
    public Plane(Point referencePoint, Vector normal) {
        this.referencePoint = referencePoint;
        this.normal = normal.normalize();
    }

    /**
     * A builder who receives 3 points and calculates the normal by himself
     * and chooses one of the points as the reference point and then initializes the variables
     */

    public Plane(Point d1, Point d2, Point d3) {
        if(d1.equals(d2) || d1.equals(d3) || d2.equals(d3))
            throw new IllegalArgumentException("There are less than 3 points");
        Vector v1=d1.subtract(d2).normalize();
        Vector v2=d3.subtract(d2).normalize();
        if(v1.equals(v2)||v1.equals(v2.scale(-1)))
            throw new IllegalArgumentException("The three points on the same line cannot be made into a plane");
        this.referencePoint = d1;
        this.normal = v1.crossProduct(v2).normalize();
    }

    @Override
    public Vector getNormal(Point p) {
        return normal;
    }

    public Vector getNormal() {
        return normal;
    }
}
