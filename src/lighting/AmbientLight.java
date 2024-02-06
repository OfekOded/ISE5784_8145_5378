package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents ambient lighting in a 3D scene. Ambient light is a constant and uniform light
 * that illuminates all objects in the scene, regardless of their position and orientation. This class extends the
 * abstract class Light and provides a convenient way to model ambient lighting effects.
 */
public class AmbientLight extends Light {

    /**
     * A predefined AmbientLight object representing no ambient light. It has zero intensity and does not contribute
     * to the illumination of the scene.
     */
    public static AmbientLight NONE = new AmbientLight(Color.BLACK, 0.0);

    /**
     * Constructs an AmbientLight object with the specified intensity and attenuation factor.
     *
     * @param iA The color intensity of the ambient light.
     * @param kA The attenuation factor for the ambient light. Should be a vector of three values (R, G, B).
     */
    public AmbientLight(Color iA, Double3 kA) {
        super(iA.scale(kA));
    }

    /**
     * Constructs an AmbientLight object with the specified intensity and a scalar attenuation factor.
     *
     * @param iA The color intensity of the ambient light.
     * @param kA The scalar attenuation factor for the ambient light. This value is applied uniformly to all color channels.
     */
    public AmbientLight(Color iA, Double kA) {
        super(iA.scale(kA));
    }
}
