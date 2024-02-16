package renderer;

import geometries.Geometry;
import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static java.lang.Math.pow;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;


/**
 * A simple ray tracer implementation that computes the color of intersection points in a scene.
 *
 * <p>This ray tracer calculates local lighting effects, such as diffuse and specular reflections, for each intersection point.</p>
 */
public class SimpleRayTracer extends RayTracerBase {

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    private static final double DELTA = 0.1; //Variable for correcting an inaccuracy in the calculation of shading rays
    public static final Double3 INITIAL_K = Double3.ONE;

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

    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray,k);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcColorGLobalEffect(constructRefractedRay(gp, ray), level, material.kT, k).add(calcColorGLobalEffect(constructReflectedRay(gp, ray), level, material.kR, k));
    }

    private Color calcColorGLobalEffect(Ray ray, int level, Double3 kx, Double3 k) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    private Ray constructRefractedRay(GeoPoint gp, Ray ray) {
        return new Ray(gp.point, ray.getDirection(), gp.geometry.getNormal(gp.point));
    }

    private Ray constructReflectedRay(GeoPoint gp, Ray ray) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = v.dotProduct(n);
        Vector r = v.subtract(n.scale(2 * nv));
        return new Ray(gp.point, r, n);
    }
    /**
     * Calculates the local lighting effects at an intersection point.
     *
     * @param gp  The intersection point.
     * @param ray The tracing ray.
     * @return The color contribution from local lighting effects.
     */
//    private Color calcLocalEffects(Intersectable.GeoPoint gp, Ray ray, Double3 k) {
//        Color color = gp.geometry.getEmission();
//        Vector n = gp.geometry.getNormal(gp.point);
//        Vector v = ray.getDirection();
//        double nv = alignZero(n.dotProduct(v));
//        if (nv == 0) return color;
//        Material material = gp.geometry.getMaterial();
//        for (LightSource lightSource : scene.lights) {
//            Vector l = lightSource.getL(gp.point);
//            double nl = alignZero(n.dotProduct(l));
//            if (nl * nv > 0) {
//                Double3 ktr = transparency(gp,lightSource,l,n);
//                if (ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
//                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
//                    color = color.add(iL.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));
//                }
//            }
//        }
//        return color;
//    }
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();    //geometry's emission

        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv)) //if the ray is tangent to the gemetry there is no specular and diffusive effects
            return color;

        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if ((nl * nv > 0)) {
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(
                            iL.scale(calcDiffusive(material, nl)
                                    .add(calcSpecular(material, n, l, nl, v))));
                }
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


//yaniv
    private Double3 transparency(GeoPoint geoPoint, LightSource lightSource, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        double lightD = lightSource.getDistance(geoPoint.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightD);
        if (intersections == null)
            return Double3.ONE;
        Double3 ktr = Double3.ONE;
        for (GeoPoint gp : intersections) {
            ktr = ktr.product(gp.geometry.getMaterial().kT);
            if (ktr.equals(Double3.ZERO))
                break;
        }
        return ktr;
    }
    //i
//    private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n,double nl){
//        Vector lightDirection = l.scale(-1); // from point to light source
//        Vector deltaVector = n.scale(nl < 0 ? DELTA : -DELTA);
//        Point point = gp.point.add(deltaVector);
//        Ray ray = new Ray(point, lightDirection);
//        List<Intersectable.GeoPoint> intersections = scene.geometries.findGeoIntersections(ray, ls.getDistance(point));
//        if (intersections == null) return true;
//        for (GeoPoint geoPoint:intersections)
//            if(geoPoint.geometry.getMaterial().kT.equals(Double3.ZERO))
//                return false;
//        return true;
//    }
    //balon
//    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {
//        Vector lightDirection = l.scale(-1); // from point to light source
//        Ray ray = new Ray(gp.point, lightDirection, n);
//        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray, light.getDistance(gp.point));
//        Double3 ktr = Double3.ONE;
//        if (intersections != null)
//            for (GeoPoint p : intersections) {
//                ktr = ktr.product(p.geometry.getMaterial().kT);
//                if (ktr.equals(Double3.ZERO))
//                    break;
//            }
//        return ktr;
//    }
}
