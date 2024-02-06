package lighting;

import primitives.Color;

/**
 * The Light class is an abstract base class representing a general light source in a 3D scene. It contains common
 * properties and methods that are shared among different types of lights. This class is designed to be extended by
 * specific light source implementations.
 */
abstract class Light {
    /**
     * The color intensity of the light source.
     */
    protected Color intensity;

    /**
     * Constructs a Light object with the specified intensity.
     *
     * @param intensity The color intensity of the light source.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Retrieves the color intensity of the light source.
     *
     * @return The color intensity of the light source.
     */
    public Color getIntensity() {
        return intensity;
    }
}
