package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The LightSource interface defines the contract for objects that represent light sources in a 3D scene. Classes that
 * implement this interface are expected to provide methods for retrieving the intensity of the light at a specific
 * point in the scene and the direction from the light source to that point.
 */
public interface LightSource {
    /**
     * Retrieves the color intensity of the light source at a given point in the scene.
     *
     * @param point The point in the scene.
     * @return The color intensity of the light source at the specified point.
     */
    Color getIntensity(Point point);

    /**
     * Retrieves the direction vector from the light source to a given point in the scene.
     *
     * @param point The point in the scene.
     * @return The direction vector from the light source to the specified point.
     */
    Vector getL(Point point);
}
