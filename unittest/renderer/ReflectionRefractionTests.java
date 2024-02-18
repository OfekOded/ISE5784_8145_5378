/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.Plane;
import geometries.Polygon;
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
                .build()
                .renderImage()
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
                .renderImage()
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
                        .setkL(4E-5).setkQ(2E-7));

        cameraBuilder.setCameraLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }

    @Test
    void customSceneTest() {
        Point A = new Point(4, 0, 0);
        Point B = new Point(3.5, 1.5, 1);
        Point C = new Point(3, 3, 0);
        Point D = new Point(1.5, 3.5, 1);
        Point E = new Point(0, 4, 0);
        Point F = new Point(1.5, 0.5, 1);
        Point G = new Point(3, -3, 0);
        Point H = new Point(-0.5, -1.5, 1);
        Point I = new Point(-4, 0, 0);
        Point J = new Point(-3.5, -1.5, 1);
        Point K = new Point(-3, -3, 0);
        Point L = new Point(-1.5, -3.5, 1);
        Point M = new Point(0, -4, 0);
        Point N = new Point(-1.5, -0.5, 1);
        Point O = new Point(-3, 3, 0);
        Point P = new Point(0.5, 1.5, 1);
        Point Q = new Point(0, 0, -5);
        Camera.Builder cameraBuilder1 = Camera.getBuilder().setDirection(Point.ZERO, new Vector(0, 0, 1)).setRayTracer(new SimpleRayTracer(scene));
        scene.geometries.add(
                new Triangle(A,B,C).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Triangle(C,D,E).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setShininess(20)),
                new Triangle(E,F,G).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(G,H,I).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(I,J,K).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(K,L,M).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(M,N,O).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(O,P,A).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(B,C,D).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(D,E,F).setEmission(new Color(BLUE))
                .setMaterial(new Material().setKr(1)),
                new Triangle(F,G,H).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(H,I,J).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(J,K,L).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(L,M,N).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(N,O,P).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(P,A,B).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),


                new Triangle(A,Q,C).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(C,Q,E).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(E,Q,G).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(G,Q,I).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(I,Q,K).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(K,Q,M).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(M,Q,O).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(O,Q,A).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKr(1))
        );
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(0, 0, 10), new Vector(0, 0, -4))
                .setkL(0.00001).setkQ(0.000005));

        cameraBuilder1.setCameraLocation(new Point(0, 50, 0)).setVpDistance(10000)
                .setVpSize(2500, 2500)
                .setImageWriter(new ImageWriter("customScene1", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
//        cameraBuilder1.setCameraLocation(new Point(0, 10, 10)).setVpDistance(10000)
//                .setVpSize(2500, 2500)
//                .setImageWriter(new ImageWriter("customScene2", 500, 500))
//                .build()
//                .renderImage()
//                .writeToImage();
//        cameraBuilder1.setCameraLocation(new Point(30, 30, 30)).setVpDistance(10000)
//                .setVpSize(2500, 2500)
//                .setImageWriter(new ImageWriter("customScene3", 500, 500))
//                .build()
//                .renderImage()
//                .writeToImage();
//        cameraBuilder1.setCameraLocation(new Point(0, 30, 0)).setVpDistance(10000)
//                .setVpSize(2500, 2500)
//                .setImageWriter(new ImageWriter("customScene4", 500, 500))
//                .build()
//                .renderImage()
//                .writeToImage();
    }
}
