package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The PointLight class represents a point light source in a 3D scene. Point lights emit light uniformly in all
 * directions from a specific point in the scene. This class extends the abstract class Light and implements the
 * LightSource interface to define the behavior of a point light source.
 */
public class PointLight extends Light implements LightSource {
    private Point position;
    private double kL = 0;
    private double kC = 1;
    private double kQ = 0;

    /**
     * Sets the linear attenuation factor for the point light.
     *
     * @param kL The linear attenuation factor.
     * @return The PointLight object with the updated linear attenuation factor.
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the constant attenuation factor for the point light.
     *
     * @param kC The constant attenuation factor.
     * @return The PointLight object with the updated constant attenuation factor.
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor for the point light.
     *
     * @param kQ The quadratic attenuation factor.
     * @return The PointLight object with the updated quadratic attenuation factor.
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Constructs a PointLight object with the specified intensity and position.
     *
     * @param intensity The color intensity of the point light.
     * @param position  The position of the point light in the scene.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Retrieves the color intensity of the point light at a given point in the scene, taking into account attenuation.
     *
     * @param point The point in the scene.
     * @return The color intensity of the point light at the specified point.
     */
    @Override
    public Color getIntensity(Point point) {
        double distance = point.distance(position);
        return intensity.scale(1 / (kC + kL * distance + kQ * distance * distance));
    }

    /**
     * Retrieves the direction vector from the point light source to a given point in the scene.
     *
     * @param point The point in the scene.
     * @return The normalized direction vector from the point light to the specified point.
     */
    @Override
    public Vector getL(Point point) {
        return point.subtract(position).normalize();
    }
}
