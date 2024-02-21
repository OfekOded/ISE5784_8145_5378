package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;

import java.util.List;
import java.util.Objects;

/**
 * The Intersectable interface represents a geometric object that can be intersected by a ray.
 */
public abstract class Intersectable {
    /**
     * Represents a geometric point of intersection between a ray and a geometry.
     */
    public static class GeoPoint {
        public Geometry geometry; // The intersected geometry
        public Point point; // The point of intersection

        public Point getPoint() {
            return point;
        }

        /**
         * Constructs a GeoPoint object with the given geometry and intersection point.
         *
         * @param geometry The intersected geometry.
         * @param point    The point of intersection.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * Checks if this GeoPoint is equal to another object.
         *
         * @param obj The object to compare with.
         * @return True if the objects are equal, false otherwise.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            return (obj instanceof GeoPoint other) && (other.point.equals(point));
        }

        /**
         * Provides a string representation of the GeoPoint object.
         *
         * @return A string representing the GeoPoint object.
         */
        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * Finds the intersection points between the geometric object and a given ray.
     *
     * @param ray The ray for which intersections are to be found.
     * @return A list of intersection points between the geometric object and the ray.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Finds the intersection points between the geometric object and a given ray.
     * @param ray
     * @param maxDistance
     * @return A list of intersection points that are at a distance less than or equal to maxDistance
     */
    public List<Point> findIntersections(Ray ray,double maxDistance) {
        var geoList = findGeoIntersections(ray,maxDistance);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Finds the intersection points between the geometric object and a given ray,
     * along with the corresponding intersected geometries.
     *
     * @param ray The ray for which intersections are to be found.
     * @return A list of GeoPoint objects representing the intersection points and the intersected geometries.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * A helper function that calls another function in order to save nvi design template
     * @param ray
     * @param maxDistance
     @return A list of GeoPoint objects representing the intersection points and the intersected geometries.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }
    /**
     * Helper method to find the intersection points between the geometric object and a given ray.
     *
     * @param ray The ray for which intersections are to be found.
     * @return A list of GeoPoint objects representing the intersection points and the intersected geometries.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);
}
