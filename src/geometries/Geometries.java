package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {
    private List<Intersectable> list = new LinkedList<Intersectable>();

    public Geometries() {
    }

    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        for (Intersectable intersectable : geometries) {
            list.add(intersectable);
        }
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersectionsPoints = null;
        for (Intersectable intersectable : list) {
            if (intersectable.findIntersections(ray) != null) {
                for (Point points : intersectable.findIntersections(ray)) {
                    if (intersectionsPoints == null)
                        intersectionsPoints = new LinkedList<Point>();
                    intersectionsPoints.add(points);
                }
            }
        }
        if (intersectionsPoints != null)
            intersectionsPoints.stream().sorted(Comparator.comparingDouble(p -> p.distance(ray.getHead()))).toList();
        return intersectionsPoints;
    }
}
