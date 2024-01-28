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
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            return (obj instanceof GeoPoint other)&&(other.point.equals(point));
        }

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

    public List<GeoPoint> findGeoIntersections(Ray ray){return findGeoIntersectionsHelper(ray);}
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray){return null;};
}
