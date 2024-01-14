package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {
    
Vector vector1 = new Vector(1,2,3);
Vector vector2 = new Vector(1,2,2);
Vector vector3 = new Vector(-1,-2,-3);
Vector vector4 = new Vector(-2,-4,-6);

    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Check the integrity of the lengthSquared()
        assertEquals(9,vector2.lengthSquared(),"ERROR: lengthSquared() wrong value");
    }

    @Test
    void length() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Check the integrity of the length()
        assertEquals(3,vector2.length(),"ERROR: length() wrong value");
    }

    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks the correctness of add()
        assertEquals(vector3,vector1.add(vector4),"ERROR: Vector + Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC10 Checks for correctness when a vector connects with its inverse
        assertThrows(IllegalArgumentException.class,()->vector1.add(vector3),"ERROR: Vector + -itself does not throw an exception");
    }

    @Test
    void subtract(){
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks the correctness of subtract()
        assertEquals(new Vector(3,6,9),vector1.subtract(vector4),"ERROR: Vector + Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC11 Checks for correctness when a vector subtracts itself
        assertThrows(IllegalArgumentException.class,()->vector1.subtract(vector1), "ERROR: Vector - itself does not throw an exception");
    }

    @Test
    void dotProduct() {
    }

    @Test
    void crossProduct() {
    }


    @Test
    void normalize() {
        // ============ Equivalence Partitions Tests ==============
        // TC01:Checks if the normal vector is a unit vector
        assertEquals(1,vector1.normalize().length(),"ERROR: the normalized vector is not a unit vector");
        // TC02:Checks if the normal vector is not reversed
        assertFalse(Util.compareSign(vector1.dotProduct(vector1.normalize()),-1),"ERROR: the normalized vector is opposite to the original one");

        // =============== Boundary Values Tests ==================
        //TC10  test that the vectors are co-lined
        assertThrows(IllegalArgumentException.class,()->vector1.crossProduct(vector1.normalize()),"ERROR: the normalized vector is not parallel to the original one");
    }
}