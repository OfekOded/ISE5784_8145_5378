package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration test class for testing the interaction between geometries, a Camera, and the rendering process.
 */
public class integrationTest {
    // Camera builder for setting up cameras in the test
    private final Camera.Builder cameraBuilder1 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(new Scene("Test")))
            .setImageWriter(new ImageWriter("Test", 1, 1))
            .setCameraLocation(Point.ZERO)
            .setDirection(new Point(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(1)
            .setVpSize(3, 3);

    // Points representing vertices of geometries
    Point pointA = new Point(0, 2, -2);
    Point pointB = new Point(0, 0, -2);
    Point pointC = new Point(1, -1, -2);
    Point pointD = new Point(-1, -1, -2);

    // Cameras used in the test
    Camera camera1 = cameraBuilder1.setVpSize(3, 3).build();
    Camera camera2 = cameraBuilder1.setCameraLocation(new Point(0, 0, 0.5)).setVpSize(3, 3).build();

    /**
     * Integration test for the method that performs some operation on a Sphere using a Camera.
     */
    @Test
    void testSphereIntegration() {
        // Create a sphere with radius 1 centered at (0, 0, -3)
        Sphere sphere1 = new Sphere(1, new Point(0, 0, -3));

        // EP01: A case where there are two intersection points
        assertEquals(2, method(sphere1, camera1, 3, 3), "ERROR: Two intersection points with the sphere");

        // Create a sphere with radius 2.5 centered at (0, 0, -2.5)
        Sphere sphere2 = new Sphere(2.5, new Point(0, 0, -2.5));

        // EP02: A case where there are 18 intersection points
        assertEquals(18, method(sphere2, camera2, 3, 3), "ERROR: 18 intersection points with the sphere");

        // Create a sphere with radius 2 centered at (0, 0, -2)
        Sphere sphere3 = new Sphere(2, new Point(0, 0, -2));

        // EP03: A case where there are 10 intersection points
        assertEquals(10, method(sphere3, camera2, 3, 3), "ERROR: 10 intersection points with the sphere");

        // Create a sphere with radius 4 centered at (0, 0, -1)
        Sphere sphere4 = new Sphere(4, new Point(0, 0, -1));

        // EP04: A case where there are 9 intersection points
        assertEquals(9, method(sphere4, camera1, 3, 3), "ERROR: 9 intersection points with the sphere");

        // Create a sphere with radius 0.5 centered at (0, 0, 1)
        Sphere sphere5 = new Sphere(0.5, new Point(0, 0, 1));

        // EP05: A case where there are 0 intersection points
        assertEquals(0, method(sphere5, camera1, 3, 3), "ERROR: 0 intersection points with the sphere");
    }

    /**
     * Integration test for the method that performs some operation on a Plane using a Camera.
     */
    @Test
    void testPlaneIntegration() {
        // Create a plane with vertices pointA, pointB, and a point at (2, 0, -2)
        Plane plane1 = new Plane(pointA, pointB, new Point(2, 0, -2));

        // Test case: Plane intersecting the entire view plane
        // The expected result is 9, as the plane intersects the entire view plane
        assertEquals(9, method(plane1, camera1, 3, 3), "ERROR: In case the plane intersects the entire view plane");

        // Create a plane with vertices pointA, pointB, and a point at (2, 0, -1)
        Plane plane2 = new Plane(pointA, pointB, new Point(2, 0, -1));

        // Test case: Plane intersecting the entire view plane at a different angle
        // The expected result is 9, as the plane intersects the entire view plane at a different depth
        assertEquals(9, method(plane2, camera1, 3, 3), "ERROR: In case the plane intersects the entire view plane at a different angle");

        // Create a plane with vertices pointA, pointB, and a point at (2, 0, 4)
        Plane plane3 = new Plane(pointA, pointB, new Point(2, 0, 4));

        // Test case: Plane partially visible in the camera's view


        // The expected result is 6, as the plane is partially visible in the camera's view
        assertEquals(6, method(plane3, camera1, 3, 3), "ERROR: In case the plane is partially visible in the view");

        // Create a plane with vertices (2, 2, 2), (0, 2, 2), and (0, 0, 2)
        Plane plane4 = new Plane(new Point(2, 2, 2), new Point(0, 2, 2), new Point(0, 0, 2));

        // Test case: Plane parallel to the camera's view plane
        // The expected result is 0, as the plane is parallel to the camera's view plane
        assertEquals(0, method(plane4, camera1, 3, 3), "ERROR: In case the plane is parallel to the view plane");
    }

    /**
     * Integration test for the method that performs some operation on a Triangle using a Camera.
     */
    @Test
    void testTriangleIntegration() {
        // Create a triangle with vertices pointC, pointD, and a point at (0, 1, -2)
        Triangle triangle1 = new Triangle(pointC, pointD, new Point(0, 1, -2));

        // EP01: A case where there is only one intersection point
        assertEquals(1, method(triangle1, camera1, 3, 3), "ERROR: In case where there is only one point of intersection between the triangle and the Vp");

        // Create a triangle with vertices pointC, pointD, and a point at (0, 20, -2)
        Triangle triangle2 = new Triangle(pointC, pointD, new Point(0, 20, -2));

        // EP02: A case where there are two intersection points
        assertEquals(2, method(triangle2, camera1, 3, 3), "ERROR: In case where there are two points of intersection between the triangle and the Vp");

        // Create a triangle with vertices (0, 1, -2), (0.5, 0, -2), and (-0.5, 0, -2)
        Triangle triangle3 = new Triangle(new Point(0, 1, -2), new Point(0.5, 0, -2), new Point(-0.5, 0, -2));

        // EP03: A case where the point of intersection is on an edge
        assertEquals(0, method(triangle3, camera1, 3, 3), "ERROR: In case where the point of intersection is on an edge");

        // Create a triangle with vertices (0, 0, -2), (0.5, 1, -2), and (-0.5, 1, -2)
        Triangle triangle4 = new Triangle(new Point(0, 0, -2), new Point(0.5, 1, -2), new Point(-0.5, 1, -2));

        // EP04: A case where the point of intersection is on a vertex
        assertEquals(0, method(triangle4, camera1, 3, 3), "ERROR: In case where the point of intersection is on a vertex");
    }

    /**
     * Calculates the number of intersections between a given Intersectable object and rays
     * constructed from a Camera's view plane, based on the specified resolution.
     *
     * @param obj     The Intersectable object to test for intersections.
     * @param camera  The Camera used to construct rays for testing intersections.
     * @param nx      The number of pixels in the X direction on the view plane.
     * @param ny      The number of pixels in the Y direction on the view plane.
     * @return The total count of intersections between the Intersectable object and the constructed rays.
     */
    int method(Intersectable obj, Camera camera, int nx, int ny) {
        // Initialize the count of intersections
        int count = 0;

        // Iterate through each pixel on the view plane
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                // Construct a ray for the current pixel
                List<Point> intersections = obj.findIntersections(camera.constructRay(nx, ny, j, i));

                // If intersections are found, increment the count
                if (intersections != null) {
                    count += intersections.size();
                }
            }
        }

        // Return the total count of intersections
        return count;
    }
}
