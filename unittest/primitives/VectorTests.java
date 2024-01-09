
//class VectorTests {
//    /**
//     * Delta value for accuracy when comparing the numbers of type 'double' in
//     * assertEquals
//     */
//    private final double DELTA = 0.000001;
//
//    @Test
//    void constructor() {
//        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0), "ERROR: zero vector does not throw an exception");
//        assertThrows(IllegalArgumentException.class, () -> new Vector(Double3.ZERO), "ERROR: zero vector does not throw an exception");
//    }
//
//    @Test
//    void add() {
//        // ============ Equivalence Partitions Tests ==============
//        // TC01: Checks the correctness of add()
//        assertEquals(new Vector(-1, -2, -3), new Vector(1, 2, 3).add(new Vector(-2, -4, -6)), "ERROR: Vector + Vector does not work correctly");
//
//        // =============== Boundary Values Tests ==================
//        //TC10 Checks for correctness when a vector connects with its inverse
//        assertThrows(IllegalArgumentException.class, () -> new Vector(1, 2, 3).add(new Vector(-1, -2, -3)), "ERROR: Vector + -itself does not throw an exception");
//
//    }
//
//    @Test
//    void dotProduct() {
//        assertEquals(0, new Vector(1, 2, 3).dotProduct(new Vector(0, 3, -2)), "ERROR: dotProduct() for orthogonal vectors is not zero");
//        assertEquals(-28, new Vector(1, 2, 3).dotProduct(new Vector(-2, -4, -6)), "ERROR: dotProduct() wrong value");
//    }
//
//    @Test
//    void crossProduct() {
//        Vector v = new Vector(1, 2, 3).crossProduct(new Vector(0, 3, -2));
//        assertThrows(IllegalArgumentException.class, () -> new Vector(1, 2, 3).crossProduct(new Vector(-2, -4, -6)), "ERROR: crossProduct() for parallel vectors does not throw an exception");
//        assertEquals(13.4907375, v.length(), DELTA, "ERROR: crossProduct() wrong result length");
//        assertEquals(0, v.dotProduct(new Vector(1, 2, 3)), "ERROR: crossProduct() result is not orthogonal to its operands");
//        assertEquals(0, v.dotProduct(new Vector(0, 3, -2)), "ERROR: crossProduct() result is not orthogonal to its operands");
//    }
//
//    @Test
//    void lengthSquared() {
//        // ============ Equivalence Partitions Tests ==============
//        // TC01: Check the integrity of the lengthSquared()
//        assertEquals(9, new Vector(1, 2, 2).lengthSquared(), "ERROR: lengthSquared() wrong value");
//    }
//
//    @Test
//    void length() {
//        // ============ Equivalence Partitions Tests ==============
//        // TC01: Check the integrity of the length()
//        assertEquals(3, new Vector(1, 2, 2).length(), "ERROR: length() wrong value");
//
//    }
//
//    @Test
//    void subtract(){
//        // ============ Equivalence Partitions Tests ==============
//        // TC01: Checks the correctness of subtract()
//        assertEquals(new Vector(3,6,9),new Vector(1,2,3).subtract(new Vector(-2,-4,-6)),"ERROR: Vector + Vector does not work correctly");
//
//        // =============== Boundary Values Tests ==================
//        //TC11 Checks for correctness when a vector subtracts itself
//        assertThrows(IllegalArgumentException.class,()->new Vector(1,2,3).subtract(new Vector(1,2,3)), "ERROR: Vector - itself does not throw an exception");
//    }
//

//
//}
/**
 * A set of test cases for the Vector class, covering various methods such as constructor, add, dotProduct, crossProduct,
 * lengthSquared, length, subtract, and normalize.
 */
package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * A set of test cases for the Vector class, covering various methods such as constructor, add, dotProduct, crossProduct,
 * lengthSquared, length, subtract, and normalize.
 */
class VectorTests {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in assertEquals.
     */
    private final double DELTA = 0.000001;

    /**
     * Test case for the constructor of the Vector class.
     */
    @Test
    void constructor() {
        // =============== Boundary Values Tests ==================
        //TC01: Checks if an exception is thrown when creating the zero vector
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0), "ERROR: zero vector does not throw an exception");
        assertThrows(IllegalArgumentException.class, () -> new Vector(Double3.ZERO), "ERROR: zero vector does not throw an exception");
    }

    /**
     * Test case for the add method of the Vector class.
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks the correctness of add()
        assertEquals(new Vector(-1, -2, -3), new Vector(1, 2, 3).add(new Vector(-2, -4, -6)),
                "ERROR: Vector + Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC10 Checks for correctness when a vector connects with its inverse
        assertThrows(IllegalArgumentException.class, () -> new Vector(1, 2, 3).add(new Vector(-1, -2, -3)),
                "ERROR: Vector + -itself does not throw an exception");
    }

    /**
     * Test case for the dotProduct method of the Vector class.
     */
    @Test
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks the correctness of dotProduct()
        assertEquals(-28, new Vector(1, 2, 3).dotProduct(new Vector(-2, -4, -6)),
                "ERROR: dotProduct() wrong value");
        // =============== Boundary Values Tests ==================
        //TC10: Testing in the case of orthogonal vectors
        assertEquals(0, new Vector(1, 2, 3).dotProduct(new Vector(0, 3, -2)),
                "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test case for the crossProduct method of the Vector class.
     */

    @Test
    void crossProduct() {
        Vector v1=new Vector(1, 2, 3);
        Vector v2=new Vector(0, 3, -2);
        Vector v = v1.crossProduct(v2); // v = (-13,2,3)
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(new Vector(-2, -4, -6)),
                "ERROR: crossProduct() for parallel vectors does not throw an exception");
        // ============ Equivalence Partitions Tests ==============
        // TC01: Check the integrity of the crossProduct()
        assertTrue(isZero(v.length() - v1.length() * v2.length()),"ERROR: crossProduct() wrong result length");
        // TC02: Check the integrity of the crossProduct() when multiplying the vector by itself
        assertTrue(isZero(v.dotProduct(v1)) && isZero(v.dotProduct(v2)),"ERROR: crossProduct() result is not orthogonal to its operands");
    }

    /**
     * Test case for the lengthSquared method of the Vector class.
     */
    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Check the integrity of the lengthSquared()
        assertEquals(9, new Vector(1, 2, 2).lengthSquared(), "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test case for the length method of the Vector class.
     */
    @Test
    void length() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Check the integrity of the length()
        assertEquals(3, new Vector(1, 2, 2).length(), "ERROR: length() wrong value");
    }

    /**
     * Test case for the subtract method of the Vector class.
     */
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Checks the correctness of subtract()
        assertEquals(new Vector(3, 6, 9), new Vector(1, 2, 3).subtract(new Vector(-2, -4, -6)),
                "ERROR: Vector + Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11 Checks for correctness when a vector subtracts itself
        assertThrows(IllegalArgumentException.class, () -> new Vector(1, 2, 3).subtract(new Vector(1, 2, 3)),
                "ERROR: Vector - itself does not throw an exception");
    }

    /**
     * Test case for the normalize method of the Vector class.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        // TC01:Checks if the normal vector is a unit vector
        assertEquals(1, new Vector(1, 2, 3).normalize().length(), "ERROR: the normalized vector is not a unit vector");
        // TC02:Checks if the normal vector is not reversed
        assertFalse(Util.compareSign(new Vector(1, 2, 3).dotProduct(new Vector(1, 2, 3).normalize()), -1), "ERROR: the normalized vector is opposite to the original one");

        // =============== Boundary Values Tests ==================
        //TC10  test that the vectors are co-lined
        assertThrows(IllegalArgumentException.class, () -> new Vector(1, 2, 3).crossProduct(new Vector(1, 2, 3).normalize()), "ERROR: the normalized vector is not parallel to the original one");
    }
}
