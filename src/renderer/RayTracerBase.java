package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * The base class for ray tracing algorithms.
 *
 * <p>Ray tracing algorithms are responsible for tracing rays through the scene and computing the color of pixels based on the intersections with scene objects.</p>
 */
public abstract class RayTracerBase {
    /** The scene to be rendered. */
    protected Scene scene;

    /**
     * Constructs a RayTracerBase with the given scene.
     *
     * @param scene The scene to be rendered.
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces the given ray through the scene and computes the color of the intersection point.
     *
     * @param ray The ray to be traced.
     * @return The color of the intersection point.
     */
    public abstract Color traceRay(Ray ray);
}

