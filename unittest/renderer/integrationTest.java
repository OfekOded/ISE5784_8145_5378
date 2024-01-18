package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class integrationTest {
    private final Camera.Builder cameraBuilder1 = Camera.getBuilder()
            .setCameraLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(1)
            .setVpSize(3, 3);
    Point pointA=new Point(0,2,-2);
    Point pointB=new Point(0,0,-2);
    Point pointC=new Point(1, -1, -2);
    Point pointD=new Point(-1, -1, -2);
    Camera camera1 = cameraBuilder1.setVpSize(3, 3).build();
    Camera camera2 = cameraBuilder1.setCameraLocation(new Point(0,0,0.5)).setVpSize(3,3).build();
    @Test
    void testSphereIntegration(){
        Sphere sphere1 = new Sphere(1, new Point(0, 0, -3));
        assertEquals(2,method(sphere1,camera1,3,3),"");
        Sphere sphere2=new Sphere(2.5,new Point(0,0,-2.5));
        assertEquals(18,method(sphere2,camera2,3,3),"");
        Sphere sphere3=new Sphere(2,new Point(0,0,-2));
        assertEquals(10,method(sphere3,camera2,3,3),"");
        Sphere sphere4=new Sphere(4,new Point(0,0,-1));
        assertEquals(9,method(sphere4,camera1,3,3));
        Sphere sphere5=new Sphere(0.5,new Point(0,0,1));
        assertEquals(0,method(sphere5,camera1,3,3),"");
    }

    @Test
    void testPlaneIntegration(){
        Plane plane1=new Plane(pointA,pointB,new Point(2,0,-2));
        assertEquals(9,method(plane1,camera1,3,3),"");
        Plane plane2=new Plane(pointA,pointB,new Point(2,0,-1));
        assertEquals(9,method(plane2,camera1,3,3),"");
        Plane plane3=new Plane(pointA,pointB,new Point(2,0,4));
        assertEquals(6,method(plane3,camera1,3,3),"");
        Plane plane4=new Plane(new Point(2,2,2),new Point(0,2,2),new Point(0,0,2));
        assertEquals(0,method(plane4,camera1,3,3),"");
    }

    @Test
    void testTriangleIntegration(){
        Triangle triangle1=new Triangle(pointC,pointD,new Point(0, 1, -2));
        assertEquals(1,method(triangle1,camera1,3,3));
        Triangle triangle2=new Triangle(pointC,pointD,new Point(0, 20, -2));
        assertEquals(2,method(triangle2,camera1,3,3),"");
        Triangle triangle3=new Triangle(new Point(0, 1, -2),new Point(0.5,0,-2),new Point(-0.5,0,-2)); //על צלע
        assertEquals(0,method(triangle3,camera1,3,3),"");
        Triangle triangle4=new Triangle(new Point(0,0,-2),new Point(0.5,1,-2),new Point(-0.5,1,-2)); // על קודקוד
        assertEquals(0,method(triangle4,camera1,3,3),"");
    }

    int method(Intersectable obj,Camera camera,int nx,int ny) {
        int count = 0;
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                List<Point> inter = obj.findIntersections(camera.constructRay(nx, ny, j, i));
                if (inter != null) count += inter.size();
            }
        }
        return count;
    }
}
