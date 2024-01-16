package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    Vector vector1 = new Vector(1,2,3);
    Vector vector2 = new Vector(1,2,2);
    Vector vector3 = new Vector(-1,-2,-3);
    Vector vector4 = new Vector(-2,-4,-6);

    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Check the integrity of the lengthSquared()
        assertEquals(9,vector2.lengthSquared(),"ERROR: lengthSquared() wrong value");
    }

    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Check the integrity of the length()
        assertEquals(3,vector2.length(),"ERROR: length() wrong value");
    }

    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks the correctness of add()
        assertEquals(vector3,vector1.add(vector4),"ERROR: Vector + Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC10 Checks for correctness when a vector connects with its inverse
        assertThrows(IllegalArgumentException.class,()->vector1.add(vector3),"ERROR: Vector + -itself does not throw an exception");
    }

    @Test
    void testSubtract(){
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks the correctness of subtract()
        assertEquals(new Vector(3,6,9),vector1.subtract(vector4),"ERROR: Vector + Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC11 Checks for correctness when a vector subtracts itself
        assertThrows(IllegalArgumentException.class,()->vector1.subtract(vector1), "ERROR: Vector - itself does not throw an exception");
    }

    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks if vectors are perpendicular return zero correctly
        assertEquals(0,vector1.dotProduct(new Vector(0,3,-2)),"ERROR: dotProduct() for orthogonal vectors is not zero");
        //TC02 Checks if dotProduct() return the correct value
        assertEquals(-28,vector1.dotProduct(vector4), "ERROR: dotProduct() wrong value");
    }

    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks if crossProduct() return the correct value
        assertEquals(Math.sqrt(182),vector1.crossProduct(new Vector(0,3,-2)).length(),"ERROR: crossProduct() wrong result length");
        // TC02: Checks crossProduct() result is orthogonal to its operands
        assertEquals(0,vector1.crossProduct(new Vector(0,3,-2)).dotProduct(vector1),"ERROR: crossProduct() wrong result length");
        assertEquals(0,vector1.crossProduct(new Vector(0,3,-2)).dotProduct(vector3),"ERROR: crossProduct() wrong result length");
        // TC03 Checks crossProduct throw an exception for parallel vectors
        assertThrows(IllegalArgumentException.class,()->vector1.crossProduct(vector4),"ERROR: crossProduct() for parallel vectors does not throw an exception");


    }


    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        // TC01:Checks if the normal vector is a unit vector
        assertEquals(1,vector1.normalize().length(),"ERROR: the normalized vector is not a unit vector");
        // TC02:Checks if the normal vector is not reversed
        assertFalse(Util.compareSign(vector1.dotProduct(vector1.normalize()),-1),"ERROR: the normalized vector is opposite to the original one");

        // =============== Boundary Values Tests ==================
        //TC10  test that the vectors are co-lined
        assertThrows(IllegalArgumentException.class,()->vector1.crossProduct(vector1.normalize()),"ERROR: the normalized vector is not parallel to the original one");
    }
    //eim
}