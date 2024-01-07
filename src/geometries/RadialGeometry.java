package geometries;

/**
 * The RadialGeometry abstract class is a base class implementing the Geometry interface.
 * It serves as a common foundation for geometrical shapes with radial characteristics, such as spheres and cylinders.
 */
public abstract class RadialGeometry implements Geometry {

    /**
     * The radius of the radial geometry.
     */
    protected final double radius;

    /**
     * Constructs a RadialGeometry object with the specified radius.
     *
     * @param radius The radius of the radial geometry.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
