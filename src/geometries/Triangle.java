package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

public class Triangle extends Polygon{
    /**A constructor that initializes the values of the 3 vertices of the triangle by using the constructor of a polygon*/
    public Triangle(Point Vertex1, Point Vertex2, Point Vertex3) {
        super(Vertex1,Vertex2,Vertex3);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
