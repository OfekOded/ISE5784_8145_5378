package primitives;
/**
This is a pds data structure
designed to help me improve the image of adaptive
sampling that I won't count the same point twice
 */
public class ColorPoint {
    public Point point;

    public ColorPoint(Point point, Color color) {
        this.point = point;
        this.color = color;
    }

    public ColorPoint(Point point) {
        this.point = point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color color;
}
