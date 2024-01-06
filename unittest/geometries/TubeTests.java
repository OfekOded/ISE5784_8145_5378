package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTests {

    @Test
    void getNormal() {
        Tube testTube = new Tube(2,new Ray(new Point(0,0,0),new Vector(1,0,0)));
        assertEquals(new Vector(0,0,1),testTube.getNormal(new Point(0,0,2)),"Tube getNormal is not working");
        assertEquals(new Vector(0,0,1),testTube.getNormal(new Point(2,0,2)),"Tube getNormal is not working");
    }
}