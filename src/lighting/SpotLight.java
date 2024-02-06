package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The SpotLight class represents a spotlight in a 3D scene. A spotlight is a type of point light source that emits light
 * in a specific direction, creating a cone of light. This class extends the PointLight class and adds the ability to
 * define the direction of the spotlight.
 */
public class SpotLight extends PointLight {
    private Vector direction;

    /**
     * Constructs a SpotLight object with the specified intensity, position, and direction.
     *
     * @param intensity  The color intensity of the spotlight.
     * @param position   The position of the spotlight in the scene.
     * @param direction  The direction vector of the spotlight.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Overrides the linear attenuation factor setting for the spotlight.
     *
     * @param kL The linear attenuation factor.
     * @return The SpotLight object with the updated linear attenuation factor.
     */
    @Override
    public SpotLight setkL(double kL) {
        return (SpotLight) super.setkL(kL);
    }

    /**
     * Overrides the constant attenuation factor setting for the spotlight.
     *
     * @param kC The constant attenuation factor.
     * @return The SpotLight object with the updated constant attenuation factor.
     */
    @Override
    public SpotLight setkC(double kC) {
        return (SpotLight) super.setkC(kC);
    }

    /**
     * Overrides the quadratic attenuation factor setting for the spotlight.
     *
     * @param kQ The quadratic attenuation factor.
     * @return The SpotLight object with the updated quadratic attenuation factor.
     */
    @Override
    public SpotLight setkQ(double kQ) {
        return (SpotLight) super.setkQ(kQ);
    }

    /**
     * Retrieves the color intensity of the spotlight at a given point in the scene, taking into account the spotlight's
     * direction and cone of illumination.
     *
     * @param point The point in the scene.
     * @return The color intensity of the spotlight at the specified point.
     */
    @Override
    public Color getIntensity(Point point) {
        if (direction.dotProduct(getL(point)) > 0)
            return super.getIntensity(point).scale(direction.dotProduct(getL(point)));
        else
            return new Color(0, 0, 0);
    }

    /**
     * Retrieves the direction vector from the spotlight to a given point in the scene.
     *
     * @param point The point in the scene.
     * @return The normalized direction vector from the spotlight to the specified point.
     */
    @Override
    public Vector getL(Point point) {
        return super.getL(point);
    }
}
