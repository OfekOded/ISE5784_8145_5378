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
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("IllegalArgumentException");
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
        if (d3.equals(Double3.ZERO))
            throw new IllegalArgumentException("IllegalArgumentException");
    }

    /**
     * Performs vector addition with another vector.
     *
     * @param vector The vector to add.
     * @return A new vector resulting from the addition.
     */
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * Scales the vector by a scalar.
     *
     * @param t The scalar to multiply with.
     * @return A new vector resulting from the scaling.
     */
    public Vector scale(double t) {
        return new Vector(xyz.scale(t));
    }

    /**
     * Calculates the dot product of this vector and another vector.
     *
     * @param vector The other vector.
     * @return The dot product of the vectors.
     */
    public double dotProduct(Vector vector) {
        Double3 helper =vector.xyz.product(xyz);
        return helper.d1+ helper.d2+ helper.d3;
    }

    /**
     * Calculates the cross product of this vector and another vector.
     *
     * @param vector The other vector.
     * @return A new vector resulting from the cross product.
     */
    public Vector crossProduct(Vector vector) {
        return new Vector((vector.xyz.d2 * xyz.d3) - (vector.xyz.d3 * xyz.d2),
                (vector.xyz.d3 * xyz.d1) - (vector.xyz.d1 * xyz.d3),
                (vector.xyz.d1 * xyz.d2) - (vector.xyz.d2 * xyz.d1));
    }

    /**
     * Calculates the squared length of the vector.
     *
     * @return The squared length of the vector.
     */
    public double lengthSquared() {
        return (xyz.d1 * xyz.d1) + (xyz.d2 * xyz.d2) + (xyz.d3 * xyz.d3);
    }

    /**
     * Calculates the length of the vector.
     *
     * @return The length of the vector.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes the vector.
     *
     * @return A new vector representing the normalized vector.
     */
    public Vector normalize() {
        return new Vector(xyz.scale(1/length()));
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
    /**
     * Indicates whether some other object is "equal to" this one.
     * This method overrides the default equals method inherited from Object.
     *
     * @param obj the object to compare this instance with.
     * @return true if the specified object is equal to this object; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if(this==obj)return true;
        return obj instanceof Vector other && super.equals(other);
    }
}
