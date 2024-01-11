package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

public class Triangle extends Polygon{
    /**A constructor that initializes the values of the 3 vertices of the triangle by using the constructor of a polygon*/
    public Triangle(Point Vertex1, Point Vertex2, Point Vertex3) {
        super(Vertex1,Vertex2,Vertex3);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        if(plane.findIntersections(ray)==null)
            return null;
        Vector v1 = vertices.getFirst().subtract(ray.getHead());
        Vector v2 = vertices.get(1).subtract(ray.getHead());
        Vector v3 = vertices.getLast().subtract(ray.getHead());
        Vector n1=v1.crossProduct(v2).normalize();
        Vector n2=v2.crossProduct(v3).normalize();
        Vector n3=v3.crossProduct(v1).normalize();
        if(((ray.getDirection().dotProduct(n1)>0) && (ray.getDirection().dotProduct(n2)>0) && (ray.getDirection().dotProduct(n3)>0))||((ray.getDirection().dotProduct(n1)<0) && (ray.getDirection().dotProduct(n2)<0) && (ray.getDirection().dotProduct(n3)<0)))
            return plane.findIntersections(ray);
        return null;
    }
}
