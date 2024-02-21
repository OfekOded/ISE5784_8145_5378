package primitives;

/**
 * The Material class represents the properties of a material used in shading calculations.
 * It defines parameters such as specular reflection (kS), diffuse reflection (kD), and shininess.
 */
public class Material {
    /**
     * The specular reflection coefficient.
     */
    public Double3 kS = Double3.ZERO;

    /**
     * The diffuse reflection coefficient.
     */
    public Double3 kD = Double3.ZERO;
    /**
     * The attenuation coefficient of transparency
     */
    public Double3 kT=Double3.ZERO;
    /**
     * attenuation coefficient of reflection
     */
    public Double3 kR=Double3.ZERO;

    /**
     * The shininess exponent affecting the size and intensity of specular highlights.
     */
    public int shininess = 1;

    /**
     * Sets the specular reflection coefficient (kS) for the material.
     *
     * @param kS The specular reflection coefficient.
     * @return The Material object with the updated specular reflection coefficient.
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }
    /**
     * Sets the reflection coefficient for this material.
     *
     * @param kr The reflection coefficient as a Double3 object representing the color components.
     * @return The Material object with the reflection coefficient set.
     */
    public Material setKr(Double3 kr) {
        this.kR = kr;
        return this;
    }

    /**
     * Sets the transparency coefficient for this material.
     *
     * @param kt The transparency coefficient as a Double3 object representing the color components.
     * @return The Material object with the transparency coefficient set.
     */
    public Material setKt(double kt) {
        this.kT = new Double3(kt);
        return this;
    }
    /**
     * Sets the reflection coefficient for this material.
     *
     * @param kr The reflection coefficient as a Double3 object representing the color components.
     * @return The Material object with the reflection coefficient set.
     */
    public Material setKr(double kr) {
        this.kR = new Double3(kr);
        return this;
    }

    /**
     * Sets the transparency coefficient for this material.
     *
     * @param kt The transparency coefficient as a Double3 object representing the color components.
     * @return The Material object with the transparency coefficient set.
     */
    public Material setKt(Double3 kt) {
        this.kT = kt;
        return this;
    }


    /**
     * Sets the specular reflection coefficient (kS) for the material using a scalar value.
     *
     * @param kS The scalar value for specular reflection coefficient.
     * @return The Material object with the updated specular reflection coefficient.
     */
    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient (kD) for the material.
     *
     * @param kD The diffuse reflection coefficient.
     * @return The Material object with the updated diffuse reflection coefficient.
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient (kD) for the material using a scalar value.
     *
     * @param kD The scalar value for diffuse reflection coefficient.
     * @return The Material object with the updated diffuse reflection coefficient.
     */
    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the shininess exponent for the material.
     *
     * @param shininess The shininess exponent.
     * @return The Material object with the updated shininess exponent.
     */
    public Material setShininess(int shininess) {
        this.shininess = shininess;
        return this;
    }
}
