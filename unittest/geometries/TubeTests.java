package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTests {

    @Test
    void getNormal() {
        assertEquals(new Vector(0,0,1),new Tube(2,new Ray(new Point(0,0,0),new Vector(1,0,0))).getNormal(new Point(0,0,2)),"the test didn't work");
         assertEquals(new Vector(0,0,1),new Tube(2,new Ray(new Point(0,0,0),new Vector(1,0,0))).getNormal(new Point(2,0,2)),"the test didn't work");
    }
}