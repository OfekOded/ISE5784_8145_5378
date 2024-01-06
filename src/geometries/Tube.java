package geometries;
import primitives.*;


public class Tube extends RadialGeometry{
    final protected Ray axis;

    /**A constructor that initializes the value of the radius vertices of the tube using the Ray constructor*/
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point p) {
        Vector v1 = p.subtract(axis.getHead());
        double t =v1.dotProduct(axis.getDirection());
        if (Util.isZero(t))
            return v1.normalize();
        Point o = axis.getHead().add(axis.getDirection().scale(t));
        return p.subtract(o).normalize();
    }
}
