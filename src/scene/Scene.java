package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a scene in a 3D environment.
 * A scene contains geometric objects, light sources, and ambient light properties.
 */
public class Scene {
    /** The list of light sources in the scene. */
    public List<LightSource> lights = new LinkedList<>();
    /** The name of the scene. */
    public String name;
    /** The background color of the scene. */
    public Color background = Color.BLACK;
    /** The ambient light in the scene. */
    public AmbientLight ambientLight = AmbientLight.NONE;
    /** The geometries present in the scene. */
    public Geometries geometries = new Geometries();

    /**
     * Constructs a Scene with the given name.
     *
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Sets the background color of the scene.
     *
     * @param background The background color to be set.
     * @return This Scene instance.
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the ambient light in the scene.
     *
     * @param ambientLight The ambient light to be set.
     * @return This Scene instance.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries present in the scene.
     *
     * @param geometries The geometries to be set.
     * @return This Scene instance.
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Sets the list of light sources in the scene.
     *
     * @param lights The list of light sources to be set.
     * @return This Scene instance.
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
