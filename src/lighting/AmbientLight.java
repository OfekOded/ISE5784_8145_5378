package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight extends Light {
    public static AmbientLight NONE = new AmbientLight(Color.BLACK, 0.0);

    public AmbientLight(Color iA, Double3 kA) {
        super(iA.scale(kA));
    }

    public AmbientLight(Color iA, Double kA) {
        super(iA.scale(kA));
    }
}
