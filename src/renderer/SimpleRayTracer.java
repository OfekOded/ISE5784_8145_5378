package renderer;

import geometries.Intersectable;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class SimpleRayTracer extends RayTracerBase{
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntersections(ray); return intersections == null ? scene.background : calcColor(ray.findClosestGeoPoint(intersections));
    }
    private Color calcColor(Intersectable.GeoPoint gp) {
        return scene.ambientLight.getIntensity()
                .add(gp.geometry.getEmission());
    }
}
