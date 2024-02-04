package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import static java.lang.Math.pow;
import static primitives.Util.alignZero;

public class SimpleRayTracer extends RayTracerBase {
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return scene.background;
        Intersectable.GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }

    private Color calcColor(Intersectable.GeoPoint gp, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(gp, ray));
    }

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
            if (nl * nv > 0) {
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(
                        iL.scale(calcDiffusive(material, nl)
                                .add(calcSpecular(material, n, l, nl, v))));
            }
        }
        return color;
    }

    private Double3 calcDiffusive(Material material, double nl) {
        if (nl < 0)
            return material.kD.scale(-1 * nl);
        return material.kD.scale(nl);
    }

    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r=l.subtract(n.scale(2*l.dotProduct(n)));
        return material.kS.scale(Math.pow(Math.max(0, -v.dotProduct(r)), material.shininess));
    }
}
