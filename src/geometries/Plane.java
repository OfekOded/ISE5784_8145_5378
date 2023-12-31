/** This class is used as a plane */
package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{

    /** A variable that keeps a reference point inside the plane */

    final private Point referencePoint;

    /** A variable that keeps the vector normal to the surface */

    final private Vector normal;

    /** A constructor that accepts a normal vector and reference point and initializes the variables */
    public Plane(Point referencePoint, Vector normal) {
        this.referencePoint = referencePoint;
        this.normal = normal.normalize();
    }

    /** A builder who receives 3 points and calculates the normal by himself
     * and chooses one of the points as the reference point and then initializes the variables
      */

    public Plane(Point d1,Point d2,Point d3) {
        this.referencePoint = d1;
        this.normal = null;
    }

    @Override
    public Vector getNormal(Point p) {
        return normal;
    }

    public Vector getNormal() {
        return normal;
    }
}
