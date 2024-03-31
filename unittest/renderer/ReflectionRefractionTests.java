/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.Plane;
import geometries.Polygon;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(Point.ZERO, new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        scene.geometries.add(
                new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setShininess(100).setKt(0.3)),
                new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(100)));
        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setkL(0.0004).setkQ(0.0000006));

        cameraBuilder.setCameraLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(150, 150)
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
                .setAntiAliasing(true)
                .adaptiveSuperSampling(true)
                .rootNumberOfRays(13)
                .build()
                .renderImage()
                .setMultithreading(6)
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        scene.geometries.add(
                new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
                .setkL(0.00001).setkQ(0.000005));

        cameraBuilder.setCameraLocation(new Point(0, 0, 10000)).setVpDistance(10000)
                .setVpSize(2500, 2500)
                .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
                .build()
                .renderImage().setMultithreading(5)
                .writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setShininess(60)),
                new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setShininess(30).setKt(0.6)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                        .setkL(4E-5).setkQ(2E-7).setSoftShadow(true).setRootNumberOfRays(9,3));

        cameraBuilder.setCameraLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("refractionShadow", 1000, 1000))
                .setAntiAliasing(true)
                .adaptiveSuperSampling(true)
                .rootNumberOfRays(13)
                .build()
                .renderImage()
                .setMultithreading(5)
                .writeToImage();
    }
    @Test
    public void mixedGeometryTest() {
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(60, 50, 100), new Vector(1, 1, -1))
                        .setkL(4E-5).setkQ(2E-7));
        Camera.Builder cameraBuilder = Camera.getBuilder()
                .setDirection(Point.ZERO, new Vector(0, 0, 1))
                .setRayTracer(new SimpleRayTracer(scene));
        Point A =new Point(0,-3,0);
        Point B =new Point(3,0,0);
        Point C =new Point(0,0,4);
        Point D =new Point(0,3,0);
        Point E =new Point(-3,0,0);
        Point F =new Point(3,3,-7);
        Point G =new Point(-3,3,-7);
        Point H =new Point(3,-3,-7);
        Point I =new Point(-3,-3,-7);
        Point J =new Point(0,0,6);
        Point K =new Point(0,-6,0);
        Point L =new Point(0,6,0);
        Point N =new Point(-6,0,0);
        Material M = new Material().setkD(0.5).setkS(0.5).setShininess(1).setKr(0.45);

        scene.geometries.add(new Triangle(A,B,C).setMaterial(M).setEmission(new Color(BLUE)),
                new Triangle(D,B,C).setMaterial(M).setEmission(new Color(RED)),
                new Triangle(A,E,C).setMaterial(M).setEmission(new Color(GREEN)),
                new Triangle(D,E,C).setMaterial(M).setEmission(new Color(YELLOW)),
                new Triangle(I,H,A).setMaterial(M).setEmission(new Color(0, 255, 255)),
                new Triangle(H,F,B).setMaterial(M).setEmission(new Color(255, 0, 255)),
                new Triangle(F,G,D).setMaterial(M).setEmission(new Color(255, 165, 0)),
                new Triangle(G,I,E).setMaterial(M).setEmission(new Color(128, 0, 128)),
                new Triangle(A,B,H).setMaterial(M).setEmission(new Color(255, 192, 203)),
                new Triangle(B,D,F).setMaterial(M).setEmission(new Color(64, 224, 208)),
                new Triangle(D,E,G).setMaterial(M).setEmission(new Color(255, 215, 0)),
                new Triangle(E,A,I).setMaterial(M).setEmission(new Color(230, 230, 250)),
                new Sphere(1,new Point(5,5,0)).setMaterial(M).setEmission(new Color(YELLOW)),
                new Sphere(1,new Point(-5,-5,5)).setMaterial(M).setEmission(new Color(YELLOW)),
                new Sphere(1,new Point(7,-5,5)).setMaterial(M).setEmission(new Color(YELLOW)),
                new Sphere(1,new Point(-5,8,5)).setMaterial(M).setEmission(new Color(YELLOW)),
                new Plane(I,new Vector(0,0,1)).setMaterial(M.setKr(1)));
        cameraBuilder.setCameraLocation(new Point(0, 50, 0)).setVpDistance(500)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("mixedGeometryTest", 2000, 2000))
                .setAntiAliasing(true)
                .adaptiveSuperSampling(true)
                .rootNumberOfRays(13)
                .build()
                .renderImage()
                .setMultithreading(6)
                .writeToImage();
        cameraBuilder.setCameraLocation(new Point(10, 50, 50)).setVpDistance(500)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("mixedGeometryTestDifferentAngele1", 2000, 2000))
                .setAntiAliasing(true)
                .adaptiveSuperSampling(true)
                .rootNumberOfRays(13)
                .build()
                .renderImage()
                .setMultithreading(6)
                .writeToImage();
        cameraBuilder.setCameraLocation(new Point(10, -50, 50)).setVpDistance(500)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("mixedGeometryTestDifferentAngele2", 2000, 2000))
                .setAntiAliasing(true)
                .adaptiveSuperSampling(true)
                .rootNumberOfRays(13)
                .build()
                .renderImage()
                .setMultithreading(6)
                .writeToImage();
        cameraBuilder.setCameraLocation(new Point(-10, -50, 50)).setVpDistance(500)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("mixedGeometryTestDifferentAngele3", 2000, 2000))
                .setAntiAliasing(true)
                .adaptiveSuperSampling(true)
                .rootNumberOfRays(13)
                .build()
                .renderImage()
                .setMultithreading(6)
                .writeToImage();
        cameraBuilder.setCameraLocation(new Point(-10, 50, 50)).setVpDistance(500)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("mixedGeometryTestDifferentAngele4", 2000, 2000))
                .setAntiAliasing(true)
                .adaptiveSuperSampling(true)
                .rootNumberOfRays(13)
                .build()
                .renderImage()
                .setMultithreading(6)
                .writeToImage();
    }
}
