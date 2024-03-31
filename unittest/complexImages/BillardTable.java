package complexImages;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import lighting.PointLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;

public class BillardTable {
    private final Scene scene = new Scene("Test scene");
    private final Camera.Builder camera1 = Camera.getBuilder()
            .setCameraLocation(new Point(100,150,150))
            .setDirection(Point.ZERO, new Vector(0, 0, 1))
            .setRayTracer(new SimpleRayTracer(scene))
            .setVpSize(150, 150)
            .setVpDistance(100)
            .setImageWriter(new ImageWriter("BillardTable Shooting angle 1", 500, 500));
    private final Camera.Builder camera2 = Camera.getBuilder()
            .setCameraLocation(new Point(150,0,100))
            .setDirection(Point.ZERO, new Vector(0, 0, 1))
            .setRayTracer(new SimpleRayTracer(scene))
            .setVpSize(150, 150)
            .setVpDistance(100)
            .setImageWriter(new ImageWriter("BillardTable Shooting angle 2", 500, 500));
    private final Camera.Builder camera3 = Camera.getBuilder()
            .setCameraLocation(new Point(0,150,100))
            .setDirection(Point.ZERO, new Vector(0, 0, 1))
            .setRayTracer(new SimpleRayTracer(scene))
            .setVpSize(150, 150)
            .setVpDistance(100)
            .setImageWriter(new ImageWriter("BillardTable Shooting angle 3", 1500, 1500))
            .setAntiAliasing(true)
            .adaptiveSuperSampling(true)
            .rootNumberOfRays(13);


        Polygon rectangle1 = new Polygon(
                new Point(-10, 10, 50), // Top-Left Front
                new Point(-10, 30, 50), // Top-Right Front
                new Point(-10, 30, 0),  // Bottom-Right Front
                new Point(-10, 10, 0)   // Bottom-Left Front
        );
        Polygon rectangle2 = new Polygon(
                new Point(-10, 30, 50), // Top-Left Front
                new Point(-30, 30, 50), // Top-Left Back
                new Point(-30, 30, 0),  // Bottom-Left Back
                new Point(-10, 30, 0)   // Bottom-Left Front
        );
        Polygon rectangle3 = new Polygon(
                new Point(-30, 30, 50), // Top-Right Back
                new Point(-30, 10, 50), // Top-Left Back
                new Point(-30, 10, 0),  // Bottom-Left Back
                new Point(-30, 30, 0)   // Bottom-Right Back
        );

        Polygon rectangle4 = new Polygon(
                new Point(-30, 10, 50), // Top-Left Back
                new Point(-10, 10, 50), // Top-Right Back
                new Point(-10, 10, 0),  // Bottom-Right Back
                new Point(-30, 10, 0)   // Bottom-Left Back
        );


        Polygon rectangle1_2 = new Polygon(
                new Point(10, 10, 50), // Top-Right Front
                new Point(10, 30, 50), // Top-Left Front
                new Point(10, 30, 0),  // Bottom-Left Front
                new Point(10, 10, 0)   // Bottom-Right Front
        );
        Polygon rectangle2_2 = new Polygon(
                new Point(10, 30, 50), // Top-Right Front
                new Point(30, 30, 50), // Top-Right Back
                new Point(30, 30, 0),  // Bottom-Right Back
                new Point(10, 30, 0)   // Bottom-Right Front
        );
        Polygon rectangle3_2 = new Polygon(
                new Point(30, 30, 50), // Top-Right Back
                new Point(30, 10, 50), // Top-Left Back
                new Point(30, 10, 0),  // Bottom-Left Back
                new Point(30, 30, 0)   // Bottom-Right Back
        );
        Polygon rectangle4_2 = new Polygon(
                new Point(30, 10, 50), // Top-Left Back
                new Point(10, 10, 50), // Top-Right Back
                new Point(10, 10, 0),  // Bottom-Right Back
                new Point(30, 10, 0)   // Bottom-Left Back
        );


        Polygon rectangle1_3 = new Polygon(
                new Point(-10, -10, 50), // Top-Left Front
                new Point(-10, -30, 50), // Top-Right Front
                new Point(-10, -30, 0),  // Bottom-Right Front
                new Point(-10, -10, 0)   // Bottom-Left Front
        );
        Polygon rectangle2_3 = new Polygon(
                new Point(-10, -30, 50), // Top-Left Front
                new Point(-30, -30, 50), // Top-Left Back
                new Point(-30, -30, 0),  // Bottom-Left Back
                new Point(-10, -30, 0)   // Bottom-Left Front
        );
        Polygon rectangle3_3 = new Polygon(
                new Point(-30, -30, 50), // Top-Right Back
                new Point(-30, -10, 50), // Top-Left Back
                new Point(-30, -10, 0),  // Bottom-Left Back
                new Point(-30, -30, 0)   // Bottom-Right Back
        );
        Polygon rectangle4_3 = new Polygon(
                new Point(-30, -10, 50), // Top-Left Back
                new Point(-10, -10, 50), // Top-Right Back
                new Point(-10, -10, 0),  // Bottom-Right Back
                new Point(-30, -10, 0)   // Bottom-Left Back
        );


        Polygon rectangle1_4 = new Polygon(
                new Point(10, -10, 50), // Top-Right Front
                new Point(10, -30, 50), // Top-Left Front
                new Point(10, -30, 0),  // Bottom-Left Front
                new Point(10, -10, 0)   // Bottom-Right Front
        );
        Polygon rectangle2_4 = new Polygon(
                new Point(10, -30, 50), // Top-Right Front
                new Point(30, -30, 50), // Top-Right Back
                new Point(30, -30, 0),  // Bottom-Right Back
                new Point(10, -30, 0)   // Bottom-Right Front
        );
        Polygon rectangle3_4 = new Polygon(
                new Point(30, -30, 50), // Top-Right Back
                new Point(30, -10, 50), // Top-Left Back
                new Point(30, -10, 0),  // Bottom-Left Back
                new Point(30, -30, 0)   // Bottom-Right Back
        );
        Polygon rectangle4_4 = new Polygon(
                new Point(30, -10, 50), // Top-Left Back
                new Point(10, -10, 50), // Top-Right Back
                new Point(10, -10, 0),  // Bottom-Right Back
                new Point(30, -10, 0)   // Bottom-Left Back
        );


        Polygon tableFloor=new Polygon(new Point(-30, 30, 50),new Point(30, 30, 50),new Point(30, -30, 50),new Point(-30, -30, 50));


        Polygon tableFloorSide1=new Polygon(new Point(-30, 30, 50),new Point(30, 30, 50),new Point(30, 30, 40),new Point(-30, 30, 40));

        Polygon tableFloorSide2=new Polygon(new Point(30, 30, 50),new Point(30, -30, 50),new Point(30, -30, 40),new Point(30, 30, 40));

        Polygon tableFloorSide3=new Polygon(new Point(30, -30, 50),new Point(-30, -30, 50),new Point(-30, -30, 40),new Point(30, -30, 40));

        Polygon tableFloorSide4=new Polygon(new Point(-30, -30, 50),new Point(-30, 30, 50),new Point(-30, 30, 40),new Point(-30, -30, 40));

        Sphere ball1=new Sphere(3,new Point(0,0,53));
        Sphere ball2=new Sphere(3,new Point(0,-6,53));
        Sphere ball4=new Sphere(3,new Point(-6,-6,53));
        Sphere ball5=new Sphere(3,new Point(6,-6,53));
        Sphere ball3=new Sphere(3,new Point(0,-12,53));
        Sphere ball6=new Sphere(3,new Point(-12,-12,53));
        Sphere ball7=new Sphere(3,new Point(12,-12,53));
        Sphere ball8=new Sphere(3,new Point(-6,-12,53));
        Sphere ball9=new Sphere(3,new Point(6,-12,53));
//        Polygon woodenBeam1 =new Polygon(new Point(-30, 30, 60),new Point(30, 30, 60),new Point(30,25,60),new Point(-30,25,60));
//        Polygon woodenBeam2 =new Polygon(new Point(30, -30, 60),new Point(-30, -30, 60),new Point(-30,-25,60),new Point(30,-25,60));
//        Polygon woodenBeam3 =new Polygon(new Point(-30, 30, 60),new Point(-25, 35, 60),new Point(25, 35, 60),new Point(30,30,60));
//        Polygon woodenBeam4 =new Polygon(new Point(-30, -30, 60),new Point(-25, -25, 60),new Point(25,-25,60),new Point(30,-30,60));
        Plane floor = new Plane(Point.ZERO,new Vector(0,0,1));
        final Color color = new Color(164,116,73);
        final Material mat = new Material().setkD(0.5).setkS(0.5).setShininess(60);
    @Test
    public void BilliardTable1(){
        scene.geometries.add(rectangle1.setMaterial(mat).setEmission(color),
                rectangle2.setMaterial(mat).setEmission(color),
                rectangle3.setMaterial(mat).setEmission(color),
                rectangle4.setMaterial(mat).setEmission(color),
                rectangle1_2.setMaterial(mat).setEmission(color),
                rectangle2_2.setMaterial(mat).setEmission(color),
                rectangle3_2.setMaterial(mat).setEmission(color),
                rectangle4_2.setMaterial(mat).setEmission(color),
                rectangle1_3.setMaterial(mat).setEmission(color),
                rectangle2_3.setMaterial(mat).setEmission(color),
                rectangle3_3.setMaterial(mat).setEmission(color),
                rectangle4_3.setMaterial(mat).setEmission(color),
                rectangle1_4.setMaterial(mat).setEmission(color),
                rectangle2_4.setMaterial(mat).setEmission(color),
                rectangle3_4.setMaterial(mat).setEmission(color),
                rectangle4_4.setMaterial(mat).setEmission(color),
                floor.setMaterial(mat).setEmission(new Color(0,165,255)),
                tableFloor.setMaterial(mat).setEmission(color),
                tableFloorSide1.setMaterial(mat).setEmission(color),
                tableFloorSide2.setMaterial(mat).setEmission(color),
                tableFloorSide3.setMaterial(mat).setEmission(color),
                tableFloorSide4.setMaterial(mat).setEmission(color),
                ball1.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball2.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball3.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball4.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball5.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball6.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball7.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball8.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball9.setMaterial(mat).setEmission(new Color(java.awt.Color.RED))
//                ,woodenBeam1.setMaterial(mat).setEmission(color),
//                woodenBeam2.setMaterial(mat).setEmission(color),
//                woodenBeam3.setMaterial(mat).setEmission(color),
//                woodenBeam4.setMaterial(mat).setEmission(color)
        );
        scene.lights.add(
                new PointLight(new Color(1000, 600, 0), new Point(-100, -100, 500))
                        .setkL(0.0004).setkQ(0.0000006));
        camera1
                .build()
                .renderImage()
                .setMultithreading(6)
                .writeToImage();
    }
    @Test
    public void BillardTable2() {
        scene.geometries.add(rectangle1.setMaterial(mat).setEmission(color),
                rectangle2.setMaterial(mat).setEmission(color),
                rectangle3.setMaterial(mat).setEmission(color),
                rectangle4.setMaterial(mat).setEmission(color),
                rectangle1_2.setMaterial(mat).setEmission(color),
                rectangle2_2.setMaterial(mat).setEmission(color),
                rectangle3_2.setMaterial(mat).setEmission(color),
                rectangle4_2.setMaterial(mat).setEmission(color),
                rectangle1_3.setMaterial(mat).setEmission(color),
                rectangle2_3.setMaterial(mat).setEmission(color),
                rectangle3_3.setMaterial(mat).setEmission(color),
                rectangle4_3.setMaterial(mat).setEmission(color),
                rectangle1_4.setMaterial(mat).setEmission(color),
                rectangle2_4.setMaterial(mat).setEmission(color),
                rectangle3_4.setMaterial(mat).setEmission(color),
                rectangle4_4.setMaterial(mat).setEmission(color),
                floor.setMaterial(mat).setEmission(new Color(0, 165, 255)),
                tableFloor.setMaterial(mat).setEmission(color),
                tableFloorSide1.setMaterial(mat).setEmission(color),
                tableFloorSide2.setMaterial(mat).setEmission(color),
                tableFloorSide3.setMaterial(mat).setEmission(color),
                tableFloorSide4.setMaterial(mat).setEmission(color),
                ball1.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball2.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball3.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball4.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball5.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball6.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball7.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball8.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball9.setMaterial(mat).setEmission(new Color(java.awt.Color.RED))
//                ,woodenBeam1.setMaterial(mat).setEmission(color),
//                woodenBeam2.setMaterial(mat).setEmission(color),
//                woodenBeam3.setMaterial(mat).setEmission(color),
//                woodenBeam4.setMaterial(mat).setEmission(color)
        );
        scene.lights.add(
                new PointLight(new Color(1000, 600, 0), new Point(-100, -100, 500))
                        .setkL(0.0004).setkQ(0.0000006));
        camera2
                .build()
                .renderImage()
                .setMultithreading(6)
                .writeToImage();
    }
    @Test
    public void BillardTable3() {
        scene.geometries.add(rectangle1.setMaterial(mat).setEmission(color),
                rectangle2.setMaterial(mat).setEmission(color),
                rectangle3.setMaterial(mat).setEmission(color),
                rectangle4.setMaterial(mat).setEmission(color),
                rectangle1_2.setMaterial(mat).setEmission(color),
                rectangle2_2.setMaterial(mat).setEmission(color),
                rectangle3_2.setMaterial(mat).setEmission(color),
                rectangle4_2.setMaterial(mat).setEmission(color),
                rectangle1_3.setMaterial(mat).setEmission(color),
                rectangle2_3.setMaterial(mat).setEmission(color),
                rectangle3_3.setMaterial(mat).setEmission(color),
                rectangle4_3.setMaterial(mat).setEmission(color),
                rectangle1_4.setMaterial(mat).setEmission(color),
                rectangle2_4.setMaterial(mat).setEmission(color),
                rectangle3_4.setMaterial(mat).setEmission(color),
                rectangle4_4.setMaterial(mat).setEmission(color),
                floor.setMaterial(mat).setEmission(new Color(0, 165, 255)),
                tableFloor.setMaterial(mat).setEmission(color),
                tableFloorSide1.setMaterial(mat).setEmission(color),
                tableFloorSide2.setMaterial(mat).setEmission(color),
                tableFloorSide3.setMaterial(mat).setEmission(color),
                tableFloorSide4.setMaterial(mat).setEmission(color),
                ball1.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball2.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball3.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball4.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball5.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball6.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball7.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball8.setMaterial(mat).setEmission(new Color(java.awt.Color.RED)),
                ball9.setMaterial(mat).setEmission(new Color(java.awt.Color.RED))
//                ,woodenBeam1.setMaterial(mat).setEmission(color),
//                woodenBeam2.setMaterial(mat).setEmission(color),
//                woodenBeam3.setMaterial(mat).setEmission(color),
//                woodenBeam4.setMaterial(mat).setEmission(color)
        );
        scene.lights.add(
                new PointLight(new Color(1000, 600, 0), new Point(-100, -100, 500))
                        .setkL(0.0004).setkQ(0.0000006).setSoftShadow(true).setRootNumberOfRays(13,3));
        camera3
                .build()
                .renderImage()
                .setMultithreading(6)
                .writeToImage();
    }
}
