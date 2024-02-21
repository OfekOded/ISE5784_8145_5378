package primitives;

/**
 * The Point class represents a point in three-dimensional space.
 * It is a primitive class used for more complex operations, such as vectors.
 * This class inherits from the Double3 class.
 */
public class Point {
    /** The field of coordinate values */
    final protected Double3 xyz;

    /** A constant Point representing the origin (0, 0, 0) */
    public static final Point ZERO = new Point(0, 0, 0);

    /**
     * Constructs a Point with the specified coordinates.
     *
     * @param d1 The x-coordinate.
     * @param d2 The y-coordinate.
     * @param d3 The z-coordinate.
     */
    public Point(double d1, double d2, double d3) {
        this.xyz = new Double3(d1, d2, d3);
    }

    /**
     * Constructs a Point with the specified Double3 object.
     *
     * @param xyz The Double3 object to initialize the Point.
     */
    Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Checks if this Point is equal to another object.
     *
     * @param obj The object to compare with this Point.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other) &&
                Util.isZero(xyz.d1 - other.xyz.d1) &&
                Util.isZero(xyz.d2 - other.xyz.d2) &&
                Util.isZero(xyz.d3 - other.xyz.d3);
    }

    /**
     * Returns a string representation of this Point.
     *
     * @return A string representation of the Point.
     */
    @Override
    public String toString() {
        return "point:" + super.toString();
    }

    /**
     * Adds the given vector to this point, returning a new point.
     *
     * @param v The vector to add.
     * @return A new point resulting from the addition.
     */
    public Point add(Vector v){
        return new Point(xyz.add(v.xyz));
    }

    /**
     * Subtracts the given point from this point, returning a new vector.
     *
     * @param tail The point to subtract.
     * @return A new vector resulting from the subtraction.
     */
    public Vector subtract(Point tail){
        return new Vector(xyz.subtract(tail.xyz));
    }

    /**
     * Calculates the squared distance between this point and another point.
     *
     * @param point The other point.
     * @return The squared distance between the points.
     */
    public Double distanceSquared(Point point){
        return (xyz.d1 - point.xyz.d1) * (xyz.d1 - point.xyz.d1) +
                (xyz.d2 - point.xyz.d2) * (xyz.d2 - point.xyz.d2) +
                (xyz.d3 - point.xyz.d3) * (xyz.d3 - point.xyz.d3);
    }

    /**
     * Calculates the distance between this point and another point.
     *
     * @param point The other point.
     * @return The distance between the points.
     */
    public double distance(Point point){
        return Math.sqrt(distanceSquared(point));
    }
}
