package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    private Point position;
    private double kL=0;
    private double kC=1;
    private double kQ=0;

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        double distance=p.distance(position);
        return intensity.scale(1/(kC+kL*distance+kQ*distance*distance));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }
}
