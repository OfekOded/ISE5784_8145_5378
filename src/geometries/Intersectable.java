package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * The Intersectable interface represents a geometric object that can be intersected by a ray.
 */
public interface Intersectable {

    /**
     * Finds the intersection points between the geometric object and a given ray.
     *
     * @param ray The ray for which intersections are to be found.
     * @return A list of intersection points between the geometric object and the ray.
     */
    List<Point> findIntersections(Ray ray);
}
