package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Represents a Triangle in a three-dimensional space.
 * Extends the Polygon class with three vertices forming a triangular shape.
 */
public class Triangle extends Polygon {

    /**
     * Constructs a Triangle with three specified vertices.
     *
     * @param vertex1 The first vertex of the triangle.
     * @param vertex2 The second vertex of the triangle.
     * @param vertex3 The third vertex of the triangle.
     */
    public Triangle(Point vertex1, Point vertex2, Point vertex3) {
        super(vertex1, vertex2, vertex3);
    }

    /**
     * Finds the intersection points between the given ray and the triangle which are at a maximum distance from the head of the ray.
     * @param ray The ray for which intersections are to be found.
     * @param maxDistance
     * @return  A list of intersection points if they exist, or null if no intersections are found.
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance){
        // Find intersections with the plane containing the polygon.
        List<Point> intersectionPoint = plane.findIntersections(ray,maxDistance);

        // If there are no intersections with the plane, return null.
        if (intersectionPoint == null)
            return null;
        Point point=intersectionPoint.getFirst();
        // Create a list to store vectors from the ray's head to each vertex of the polygon.
        List<Vector> vectorsList = new LinkedList<>();
        for (Point vertex : vertices)
            vectorsList.add(vertex.subtract(ray.getHead()));

        // Create a list to store normals of the edges formed by consecutive vectors.
        List<Vector> normals = new LinkedList<>();
        for (int i = 0; i < 3; i++)
            normals.add(vectorsList.get(i).crossProduct(vectorsList.get((i + 1) % vectorsList.size())));

        // Initialize a flag to check if all normals have the same sign with respect to the ray's direction.
        boolean flag = true;

        // Check the sign consistency of the dot products between each normal and the ray's direction.
        for (int i = 0; i < 3; i++)
            if (!Util.compareSign(normals.get(i).dotProduct(ray.getDirection()), normals.get((i + 1) % normals.size()).dotProduct(ray.getDirection())))
                flag = false;

        // If all dot products have the same sign, return the intersection points.
        if (flag)
            return List.of(new GeoPoint(this, point));

        // If the signs are inconsistent, return null.
        return null;
    }

}
