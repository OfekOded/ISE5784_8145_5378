package primitives;
public class Ray
{
    final private Point head;
    final private Vector direction;

    public Ray(Point p,Vector v)
    {
        this.head=p;
        this.direction=v.normalize();
    }
}