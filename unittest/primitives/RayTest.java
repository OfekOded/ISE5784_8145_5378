package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void getPoint() {
        Ray ray = new Ray(new Point(1,0,0),new Vector(1,0,0));
        // ============ Equivalence Partitions Tests ==============
        //TC01: test when t is positive
        assertEquals(new Point(2,0,0),ray.getPoint(1),"Error in getPoint test when t is positive");
        //TC02: test when t is negative
        assertEquals(new Point(0,0,0),ray.getPoint(-1),"Error in getPoint test when t is negative");
        // =============== Boundary Values Tests ==================
        //TC01: test when t is zero
        assertEquals(new Point(1,0,0),ray.getPoint(0),"Error in getPoint test when t is zero");
    }
}