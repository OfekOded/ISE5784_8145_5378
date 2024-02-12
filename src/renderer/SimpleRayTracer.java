package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static java.lang.Math.pow;
import static primitives.Util.alignZero;


/**
 * A simple ray tracer implementation that computes the color of intersection points in a scene.
 *
 * <p>This ray tracer calculates local lighting effects, such as diffuse and specular reflections, for each intersection point.</p>
 */
public class SimpleRayTracer extends RayTracerBase {

    private static final double DELTA = 0.1; //Variable for correcting an inaccuracy in the calculation of shading rays

    /**
     * Constructs a SimpleRayTracer with the given scene.
     *
     * @param scene The scene to be rendered.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces the given ray through the scene and computes the color of the intersection point.
     *
     * @param ray The ray to be traced.
     * @return The color of the intersection point.
     */
    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return scene.background;
        Intersectable.GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color of an intersection point based on local lighting effects.
     *
     * @param gp  The intersection point.
     * @param ray The tracing ray.
     * @return The color of the intersection point.
     */
    private Color calcColor(Intersectable.GeoPoint gp, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(gp, ray));
    }

    /**
     * Calculates the local lighting effects at an intersection point.
     *
     * @param gp  The intersection point.
     * @param ray The tracing ray.
     * @return The color contribution from local lighting effects.
     */
    private Color calcLocalEffects(Intersectable.GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if ((nl * nv > 0) && unshaded(gp,lightSource, l, n,nl)) {
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(
                        iL.scale(calcDiffusive(material, nl)
                                .add(calcSpecular(material, n, l, nl, v))));
            }
        }
        return color;
    }

    /**
     * Calculates the diffuse reflection component at an intersection point.
     *
     * @param material The material of the intersected geometry.
     * @param nl       The dot product of the normal vector and the light vector.
     * @return The diffuse reflection component.
     */
    private Double3 calcDiffusive(Material material, double nl) {
        if (nl < 0)
            return material.kD.scale(-1 * nl);
        return material.kD.scale(nl);
    }

    /**
     * Calculates the specular reflection component at an intersection point.
     *
     * @param material The material of the intersected geometry.
     * @param n        The normal vector at the intersection point.
     * @param l        The light vector.
     * @param nl       The dot product of the normal vector and the light vector.
     * @param v        The view vector.
     * @return The specular reflection component.
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl));
        return material.kS.scale(Math.pow(Math.max(0, -v.dotProduct(r)), material.shininess));
    }

    /**
     * A boolean function that returns true if the ray is not blocked and really affects the image
     * @param gp
     * @param light
     * @param l
     * @param n
     * @param nl
     * @return If the ray is blocked or not
     */
    private boolean unshaded(Intersectable.GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector deltaVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(deltaVector);
        Ray ray = new Ray(point, lightDirection);
        List<Intersectable.GeoPoint> intersections = scene.geometries.findGeoIntersections(ray, light.getDistance(point));
        if (intersections == null) return true;
        return false;
    }
}
