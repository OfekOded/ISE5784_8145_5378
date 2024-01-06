package primitives;
/**
* The Ray class is a basic class.
 * is used by us in the more complicated geometric shapes,
 * such as a cylinder, etc
 */

public class Ray
{
    /**The variable stores a point representing the head of the ray*/
    final private Point head;
    /**The variable stores the direction of the ray, the variable is of vector type*/
    final private Vector direction;

    /**get function for the head variable*/
    public Point getHead() {
        return head;
    }
    /**get function for the direction variable*/
    public Vector getDirection() {
        return direction;
    }

   /** A constructor that initializes the ray according to values received from the user*/
    public Ray(Point p, Vector v)
    {
        this.head=p;
        this.direction=v.normalize();
    }
}