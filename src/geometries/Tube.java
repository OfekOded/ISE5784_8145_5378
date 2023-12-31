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
        return null;
    }
}
