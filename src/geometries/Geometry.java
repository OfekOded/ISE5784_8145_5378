package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public abstract class Geometry extends Intersectable {
    private Material material=new Material();
    protected Color emission=Color.BLACK;

    public Color getEmission() {
        return emission;
    }

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

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public Material getMaterial() {
        return material;
    }
}
