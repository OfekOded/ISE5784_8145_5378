package primitives;

import java.util.Comparator;
import java.util.List;

import static primitives.Util.isZero;

/**
 * The Ray class represents a geometric ray in three-dimensional space.
 * It is used in more complex geometric shapes such as cylinders, etc.
 */
public class Ray {
    /**
     * The variable stores a point representing the head of the ray.
     */
    final private Point head;

    /**
     * The variable stores the direction of the ray, the variable is of vector type.
     */
    final private Vector direction;

    /**
     * Constructs a new Ray object with the given head point and direction vector.
     * The direction vector is normalized during construction.
     *
     * @param point The head point of the ray.
     * @param vector The direction vector of the ray.
     */
    public Ray(Point point, Vector vector) {
        this.head = point;
        this.direction = vector.normalize();
    }

    /**
     * Retrieves the head point of the ray.
     *
     * @return The head point of the ray.
     */
    public Point getHead() {
        return head;
    }

    /**
     * Retrieves the direction vector of the ray.
     *
     * @return The direction vector of the ray.
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * Calculates and returns a point along the ray at a given parameter value 't'.
     *
     * @param t The parameter value along the ray.
     * @return A point along the ray at the specified parameter value.
     */
    public Point getPoint(double t) {
        if(isZero(t))
            return head;
        return head.add(direction.scale(t));
    }

    public Point findClosestPoint(List<Point> list) {
        if (list != null){
            double distance = this.head.distance(list.getFirst());
            Point closestPoint=list.getFirst();
            for(int i=1;i<list.size();i++)
            {
             double newDistance=this.head.distance(list.get(i));
             if(newDistance<distance)
             {
                 distance=newDistance;
                 closestPoint=list.get(i);
             }
            }
            return closestPoint;
     }
        return null;
    }
}
