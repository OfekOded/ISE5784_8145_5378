package renderer;

import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class integrationTest {
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setCameraLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(1)
            .setVpSize(3, 3);

    @Test
    void testSphereIntegration(){
        Camera camera = cameraBuilder.setVpSize(3, 3).build();

        Sphere sphere = new Sphere(1, new Point(0, 0, -3));
        int count = 0;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                List<Point> inter = sphere.findIntersections(camera.constructRay(3, 3, j, i);
                if (inter != null) count = inter.size();
            }
        }
        // Integration test with plane
        // Integration test with triangle
    }

    int method() {
        return 3;
    }
}
