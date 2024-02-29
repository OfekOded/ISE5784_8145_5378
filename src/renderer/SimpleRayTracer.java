package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import lighting.PointLight;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

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

    /**
     * Calculates the color of a point in the scene.
     *
     * @param geoPoint The intersection point in the scene.
     * @param ray      The ray that intersects the point.
     * @return The color of the point in the scene.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    /**
     * Recursively calculates the color of a point in the scene, considering local and global effects.
     *
     * @param intersection The intersection point in the scene.
     * @param ray          The ray that intersects the point.
     * @param level        The recursion level.
     * @param k            Coefficient representing the attenuation of light.
     * @return The color of the point considering local and global effects.
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * Calculates the global effects (refraction and reflection) of light at a given point.
     *
     * @param gp    The intersection point in the scene.
     * @param ray   The ray that intersects the point.
     * @param level The recursion level.
     * @param k     Coefficient representing the attenuation of light.
     * @return The color of the point considering global effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcColorGLobalEffect(constructRefractedRay(gp, ray), level, material.kT, k).add(calcColorGLobalEffect(constructReflectedRay(gp, ray), level, material.kR, k));
    }

    /**
     * Calculates the color contribution from a global effect (refraction or reflection).
     *
     * @param ray   The ray representing the global effect (refracted or reflected ray).
     * @param level The recursion level.
     * @param kx    Coefficient representing the attenuation of light for the specific effect.
     * @param k     Coefficient representing the overall attenuation of light.
     * @return The color contribution from the global effect.
     */
    private Color calcColorGLobalEffect(Ray ray, int level, Double3 kx, Double3 k) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * Finds the closest intersection point of a ray with objects in the scene.
     *
     * @param ray The ray to trace.
     * @return The closest intersection point with objects in the scene.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /**
     * Constructs a refracted ray at a given intersection point.
     *
     * @param gp  The intersection point in the scene.
     * @param ray The incident ray.
     * @return The refracted ray.
     */
    private Ray constructRefractedRay(GeoPoint gp, Ray ray) {
        return new Ray(gp.point, ray.getDirection(), gp.geometry.getNormal(gp.point));
    }

    /**
     * Constructs a reflected ray at a given intersection point.
     *
     * @param gp  The intersection point in the scene.
     * @param ray The incident ray.
     * @return The reflected ray.
     */
    private Ray constructReflectedRay(GeoPoint gp, Ray ray) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = v.dotProduct(n);
        Vector r = v.subtract(n.scale(2 * nv));
        return new Ray(gp.point, r, n);
    }

    /**
     * Calculates the local effects (diffuse and specular) of light at a given geometric point.
     *
     * @param gp  The geometric point.
     * @param ray The ray from the camera to the geometric point.
     * @param k   Coefficient representing the attenuation of light due to various factors.
     * @return The color representing the local effects of light at the geometric point.
     */
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

    /**
     * Calculates the transparency of a surface at a given point when illuminated by a light source.
     *
     * @param geoPoint    The point on the surface.
     * @param lightSource The light source illuminating the scene.
     * @param l           The direction vector from the point towards the light source.
     * @param n           The normal vector at the given point on the surface.
     * @return The transparency coefficient of the surface at the given point.
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource lightSource, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        double lightD = lightSource.getDistance(geoPoint.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightD);
        if (intersections == null)
            return Double3.ONE;
        Double3 ktr = Double3.ONE;
        if (((PointLight) lightSource).softShadow)
            return softShadow(((PointLight) lightSource), lightDirection, geoPoint.point, n);
        else
            for (GeoPoint gp : intersections) {
                ktr = ktr.product(gp.geometry.getMaterial().kT);
                if (ktr.equals(Double3.ZERO))
                    break;
            }
        return ktr;
    }

    private Double3 softShadow(PointLight pointLight, Vector lightDirection, Point point,Vector n) {
        Double3 avrageKtr = Double3.ZERO;
        Vector vUp, vRight;
        if (lightDirection.equals(new Vector(1, 0, 0)) || lightDirection.equals(new Vector(-1, 0, 0)))
            vUp = new Vector(0, 0, 1);
        else
            vUp = lightDirection.crossProduct(new Vector(1, 0, 0));
        vRight = vUp.crossProduct(lightDirection);
        pointLight.grid.rayBeam(pointLight.position, vUp, vRight);
        for (Point gridPoint : pointLight.grid.grid) {
            Double3 ktr = Double3.ONE;
            Ray lightRay = new Ray(point,gridPoint.subtract(point), n);
            double lightD = gridPoint.distance(point);
            List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightD);
            if (intersections == null)
                ktr = Double3.ONE;
            else
                for (GeoPoint gp : intersections) {
                    ktr = ktr.product(gp.geometry.getMaterial().kT);
                    if (ktr.equals(Double3.ZERO))
                        break;
                }
            avrageKtr = avrageKtr.add(ktr);
        }
        return avrageKtr.scale(1 / (Math.pow(pointLight.grid.rootNumberOfRays, 2)));
    }
}
