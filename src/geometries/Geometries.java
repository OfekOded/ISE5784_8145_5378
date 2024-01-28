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
public class Geometries extends Intersectable {
    /**
     * The list of intersectable geometries in this collection.
     */
    private final List<Intersectable> geometries = new LinkedList<>();

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
        Collections.addAll(this.geometries, geometries);
    }

    /**
     * Finds the intersection points between the given ray and the geometries in the collection.
     *
     * @param ray The ray to find intersections with.
     * @return A list of intersection points, or null if there are no intersections.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        List<GeoPoint> intersections = null;
        for (Intersectable intersectable : geometries) {
            if (intersectable.findGeoIntersectionsHelper(ray) != null) {
                for (GeoPoint geoPoints : intersectable.findGeoIntersectionsHelper(ray)) {
                    if (intersections == null)
                        intersections = new LinkedList<>();
                    intersections.add(geoPoints);
                }
            }
        }
        if (intersections != null)
            intersections.stream().sorted(Comparator.comparingDouble(p -> p.point.distance(ray.getHead()))).toList();
        return intersections;
    }
}
