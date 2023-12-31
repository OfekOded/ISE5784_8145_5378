/** An abstract class on which all shapes will be based */
package geometries;

import primitives.Vector;
import primitives.Point;


public interface Geometry {
    /** An abstract method that accepts a point and returns a normal vector */

    public abstract Vector getNormal(Point p);
}
