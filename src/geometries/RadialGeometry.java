package geometries;

/**Radial Geometry abstract class that implements the Geometry interface*/
public abstract class RadialGeometry implements Geometry{
    protected final double radius;

    /**A constructor that accepts a value for the radius as a parameter and initializes the field accordingly*/
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
