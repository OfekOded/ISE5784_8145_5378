package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;


public class SpotLight extends PointLight {
    private Vector direction;

    @Override
    public SpotLight setkL(double kL) {
        return (SpotLight) super.setkL(kL);
    }

    @Override
    public SpotLight setkC(double kC) {
        return (SpotLight) super.setkC(kC);
    }

    @Override
    public SpotLight setkQ(double kQ) {
        return (SpotLight) super.setkQ(kQ);
    }

    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        if(direction.dotProduct(getL(p))>0)
            return super.getIntensity(p).scale(direction.dotProduct(getL(p)));
        else
            return new Color(0,0,0);
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
