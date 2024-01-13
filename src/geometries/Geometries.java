package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a collection of geometric objects that implement the Intersectable interface.
 * This class provides methods for adding intersectable geometries and finding intersections with a given ray.
 */
public class Geometries implements Intersectable {
    /**
     * The list of intersectable geometries in this collection.
     */
    private final List<Intersectable> list = new LinkedList<>();

    /**
     * Constructs an empty Geometries object.
     */
    public Geometries() {}

    /**
     * Constructs a Geometries object with the specified intersectable geometries.
     *
     * @param geometries The intersectable geometries to add to the collection.
     */
    public Geometries(Intersectable... geometries) {
        if (geometries != null) {
            add(geometries);
        }
    }

    /**
     * Adds the specified intersectable geometries to the collection.
     *
     * @param geometries The intersectable geometries to add.
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(list, geometries);
    }

    /**
     * Finds the intersection points between the given ray and the geometries in the collection.
     *
     * @param ray The ray to find intersections with.
     * @return A list of intersection points, or null if there are no intersections.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersectionsPoints = null;
        for (Intersectable intersectable : list) {
            if (intersectable.findIntersections(ray) != null) {
                for (Point points : intersectable.findIntersections(ray)) {
                    if (intersectionsPoints == null)
                        intersectionsPoints = new LinkedList<>();
                    intersectionsPoints.add(points);
                }
            }
        }
        if (intersectionsPoints != null)
            intersectionsPoints = intersectionsPoints.stream()
                    .sorted(Comparator.comparingDouble(p -> p.distance(ray.getHead())))
                    .toList();
        return intersectionsPoints;
    }
}
