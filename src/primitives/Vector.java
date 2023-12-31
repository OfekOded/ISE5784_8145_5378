package primitives;
/**The Vector class inherits from the Point class */
public class Vector extends Point {
    /**A constructor that initializes the three point values in a vector and throws an error with the vector is the 0 vector*/
    public Vector(double x,double y ,double z)
    {
        super(x,y,z);
        if (xyz.equals(Double3.ZERO)) throw new IllegalArgumentException("IllegalArgumentException");
    }
    /**A constructor that initializes the values of the three points in a vector and receives a member of type Double3 and throws an error with the vector is the 0 vector*/
    public Vector(Double3 d3)
    {
        super(d3);
        if (d3.equals(Double3.ZERO)) throw new IllegalArgumentException("IllegalArgumentException");
    }

    /**A function that performs vector addition*/
    public Vector add(Vector v1)
    {
        return new Vector(xyz.add(v1.xyz));
    }

    /**A function that performs the vector multiplication by a scalar*/
    public Vector scale(double num) // need to take care
    {
        return new Vector(xyz.scale(num));
    }

    /**A function that performs scalar multiplication*/
    public double dotProduct(Vector v1)
    {
        return (v1.xyz.d1*this.xyz.d1)+(v1.xyz.d2*this.xyz.d2)+(v1.xyz.d3*this.xyz.d3);
    }

    /**A function that performs vector multiplication*/
    public Vector crossProduct(Vector v1)
    {
        return new Vector((v1.xyz.d2*this.xyz.d3)-(v1.xyz.d3*this.xyz.d2),(v1.xyz.d3*this.xyz.d1)-(v1.xyz.d1*this.xyz.d3),(v1.xyz.d1*this.xyz.d2)-(v1.xyz.d2*this.xyz.d1));
    }

    /**A function that calculates the squared length of a vector*/
    public double lengthSquared()
    {
        return (this.xyz.d1*this.xyz.d1)+(this.xyz.d2*this.xyz.d2)+(this.xyz.d3*this.xyz.d3);
    }

    /**A function that calculates the length of a vector*/
    public double length()
    {
        return Math.sqrt(this.lengthSquared());
    }

    /**A function that calculates the normal vector*/
    public Vector normalize()
    {
        return new Vector(this.xyz.d1/this.length(),this.xyz.d2/this.length(),this.xyz.d3/this.length());
    }

    @Override
    public String toString() {
        return "Vector{}";
    }
}