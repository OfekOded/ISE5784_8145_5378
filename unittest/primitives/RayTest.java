package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void getPoint() {
        Ray ray = new Ray(new Point(1,0,0),new Vector(1,0,0));
        // ============ Equivalence Partitions Tests ==============
        //TC01: The point on the side
        assertEquals(new Point(2,0,0),ray.getPoint(1),"");
        //TC02: The point on the side
        assertEquals(new Point(0,0,0),ray.getPoint(-1),"");
        // =============== Boundary Values Tests ==================
        //TC01: The point on the side
        assertEquals(new Point(1,0,0),ray.getPoint(0),"");
    }
}