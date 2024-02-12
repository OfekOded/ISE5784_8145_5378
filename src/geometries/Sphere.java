/**
 * The Sphere class represents a three-dimensional spherical geometry.
 * It extends the RadialGeometry abstract class and implements the Geometry interface.
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import javax.management.openmbean.OpenMBeanOperationInfo;
import java.util.Comparator;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Sphere class represents a three-dimensional spherical geometry.
 * It extends the RadialGeometry abstract class and implements the Geometry interface.
 */
public class Sphere extends RadialGeometry {

    /**
     * The center point of the sphere.
     */
    final private Point center;

    /**
     * Constructs a Sphere object with the specified radius and center.
     *
     * @param radius The radius of the sphere.
     * @param center The center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Calculates and returns the normal vector at a specified point on the surface of the sphere.
     *
     * @param point The point on the surface for which the normal vector is to be calculated.
     * @return The normal vector at the specified point.
     */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(this.center).normalize();
    }


    /**
     * Finds the intersection points between the given ray and the sphere which are at a maximum distance from the head of the ray.
     * @param ray The ray for which intersections are to be found.
     * @param maxDistance
     * @return  A list of intersection points with the sphere or null if there are no intersections.
     */
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        double d = 0, tm = 0;
        //if head is not the center, calculate tm (the scaling product to get to the point in the middle of the ray part inside the sphere)
        //and d (tm point's distance from center)
        if (!ray.getHead().equals(center)) {
            Vector vector = center.subtract(ray.getHead());
            tm = alignZero(ray.getDirection().dotProduct(vector));
            d = alignZero(Math.sqrt(vector.lengthSquared() - tm * tm));
        }

        if (d < radius) {    //if the ray is inside the sphere
            //th = the scaling product to get to the sphere from tm point
            double th = alignZero(Math.sqrt(radius * radius - d * d));
            double t1 = tm + th;
            double t2 = tm - th;
            if (t1 > 0 && alignZero(t1-maxDistance)<=0) {   //if t1 is in the ray's direction
                Point p1 = ray.getPoint(t1);
                if (t2 > 0 && alignZero(t2-maxDistance)<=0) {   //if t2 is in the ray's direction
                    Point p2 = ray.getPoint(t2);
                    if (p1.distance(ray.getHead()) < p2.distance(ray.getHead()))
                        return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
                    return List.of(new GeoPoint(this, p2), new GeoPoint(this, p1));
                }
                return List.of(new GeoPoint(this, p1));

            } else if (t2 > 0 && alignZero(t2-maxDistance)<=0) {    //if t2 is in the ray's direction (and t1 is not)
                Point p2 = ray.getPoint(t2);
                return List.of(new GeoPoint(this, p2));
            }
        }
        return null;
    }
}
