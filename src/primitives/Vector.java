package primitives;

/**
 * The Vector class represents a vector in three-dimensional space.
 * It inherits from the Point class.
 */
public class Vector extends Point {

    /**
     * Constructs a Vector with the specified x, y, and z coordinates.
     * Throws an IllegalArgumentException if the vector is the zero vector.
     *
     * @param x The x-coordinate of the vector.
     * @param y The y-coordinate of the vector.
     * @param z The z-coordinate of the vector.
     * @throws IllegalArgumentException If the vector is the zero vector.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) throw new IllegalArgumentException("IllegalArgumentException");
    }

    /**
     * Constructs a Vector with the specified Double3 object.
     * Throws an IllegalArgumentException if the vector is the zero vector.
     *
     * @param d3 The Double3 object to initialize the Vector.
     * @throws IllegalArgumentException If the vector is the zero vector.
     */
    public Vector(Double3 d3) {
        super(d3);
        if (d3.equals(Double3.ZERO)) throw new IllegalArgumentException("IllegalArgumentException");
    }

    /**
     * Performs vector addition with another vector.
     *
     * @param v1 The vector to add.
     * @return A new vector resulting from the addition.
     */
    public Vector add(Vector v1) {
        return new Vector(xyz.add(v1.xyz));
    }

    /**
     * Scales the vector by a scalar.
     *
     * @param num The scalar to multiply with.
     * @return A new vector resulting from the scaling.
     */
    public Vector scale(double num) {
        return new Vector(xyz.scale(num));
    }

    /**
     * Calculates the dot product of this vector and another vector.
     *
     * @param v1 The other vector.
     * @return The dot product of the vectors.
     */
    public double dotProduct(Vector v1) {
        return (v1.xyz.d1 * this.xyz.d1) + (v1.xyz.d2 * this.xyz.d2) + (v1.xyz.d3 * this.xyz.d3);
    }

    /**
     * Calculates the cross product of this vector and another vector.
     *
     * @param v1 The other vector.
     * @return A new vector resulting from the cross product.
     */
    public Vector crossProduct(Vector v1) {
        return new Vector((v1.xyz.d2 * this.xyz.d3) - (v1.xyz.d3 * this.xyz.d2),
                (v1.xyz.d3 * this.xyz.d1) - (v1.xyz.d1 * this.xyz.d3),
                (v1.xyz.d1 * this.xyz.d2) - (v1.xyz.d2 * this.xyz.d1));
    }

    /**
     * Calculates the squared length of the vector.
     *
     * @return The squared length of the vector.
     */
    public double lengthSquared() {
        return (this.xyz.d1 * this.xyz.d1) + (this.xyz.d2 * this.xyz.d2) + (this.xyz.d3 * this.xyz.d3);
    }

    /**
     * Calculates the length of the vector.
     *
     * @return The length of the vector.
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Normalizes the vector.
     *
     * @return A new vector representing the normalized vector.
     */
    public Vector normalize() {
        return new Vector(this.xyz.d1 / this.length(), this.xyz.d2 / this.length(), this.xyz.d3 / this.length());
    }

    /**
     * Returns a string representation of the vector.
     *
     * @return A string representation of the vector.
     */
    @Override
    public String toString() {
        return "Vector{}";
    }
}
