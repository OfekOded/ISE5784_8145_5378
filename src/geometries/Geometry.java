package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * Represents a geometric object in three-dimensional space.
 * This class provides methods for setting and getting material properties, emission color,
 * and calculating the normal vector at a specified point on the surface of the geometry.
 */
public abstract class Geometry extends Intersectable {
    private Material material = new Material(); // Material properties of the geometry
    protected Color emission = Color.BLACK; // Emission color of the geometry

    /**
     * Retrieves the emission color of the geometry.
     *
     * @return The emission color of the geometry.
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission The emission color to set.
     * @return The geometry object with the updated emission color.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Calculates and returns the normal vector at the specified point on the surface of the geometry.
     *
     * @param point The point on the surface for which the normal vector is to be calculated.
     * @return The normal vector at the specified point.
     */
    public abstract Vector getNormal(Point point);

    /**
     * Sets the material properties of the geometry.
     *
     * @param material The material properties to set.
     * @return The geometry object with the updated material properties.
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Retrieves the material properties of the geometry.
     *
     * @return The material properties of the geometry.
     */
    public Material getMaterial() {
        return material;
    }
}
