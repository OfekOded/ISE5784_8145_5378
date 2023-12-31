package primitives;
/**
 *The Point class is a primitive class that will help us do more complex things
 * like vector etc. The class inherits from the Double3 class
 */
public class Point {
    /** The field of coordinate values */
    final protected Double3 xyz;

    /** I had to add this in order to fix the main */
    public static final Point ZERO = new Point(0,0,0);

    /** Constructor that receives three coordinates and initializes Point */

    public Point(double d1,double d2, double d3) {
        this.xyz = new Double3(d1,d2,d3);
    }

    /** Constructor that accepts an object of type Double3 and initializes Point */

    Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /** According to the instructions, there is no need to write comments */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other) &&
                Util.isZero(xyz.d1 - other.xyz.d1) &&
                Util.isZero(xyz.d2 - other.xyz.d2) &&
                Util.isZero(xyz.d3 - other.xyz.d3);
    }

    /** According to the instructions, there is no need to write comments */

    @Override
    public String toString() {
        return "point:" + super.toString();
    }

    /** The method returns the point of this object plus the vector that the method received */

    public Point add(Vector v){
        return new Point(xyz.add(v.xyz));
    }

    /** The method returns a vector by subtracting the head (the point of this object) minus the tail (the object we got) */

    public Vector subtract(Point tail){
       return new Vector(xyz.subtract(tail.xyz));
    }

    /** The function returns the distance between the point of this object and the object we received squared */

    public Double distanceSquared(Point anotherPoint){
        return (xyz.d1-anotherPoint.xyz.d1)*(this.xyz.d1-anotherPoint.xyz.d1)+
                (this.xyz.d2-anotherPoint.xyz.d2)*(this.xyz.d2-anotherPoint.xyz.d2)+
                (this.xyz.d3-anotherPoint.xyz.d3)*(this.xyz.d3-anotherPoint.xyz.d3);
    }

    /** The function returns the distance between the point of this object and the object we received */

    public double distance(Point anotherPoint){
        return Math.sqrt(distanceSquared(anotherPoint));
    }
}
