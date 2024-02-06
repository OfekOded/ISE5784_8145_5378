package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The DirectionalLight class represents a directional light source in a 3D scene. Unlike point lights, directional lights
 * have a specified direction and illuminate all objects in the scene uniformly from that direction. This class extends
 * the abstract class Light and implements the LightSource interface to define the behavior of a light source.
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector direction;

    /**
     * Constructs a DirectionalLight object with the specified intensity and direction.
     *
     * @param intensity The color intensity of the directional light.
     * @param direction The direction of the light represented as a normalized vector.
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Retrieves the intensity of the directional light at a given point in the scene.
     *
     * @param point The point in the scene.
     * @return The color intensity of the directional light.
     */
    @Override
    public Color getIntensity(Point point) {
        return intensity;
    }

    /**
     * Retrieves the direction from the light source to a given point in the scene. For directional lights, this direction
     * is constant and does not depend on the specific point in the scene.
     *
     * @param point The point in the scene.
     * @return The normalized direction vector of the light source.
     */
    @Override
    public Vector getL(Point point) {
        return direction;
    }
}
