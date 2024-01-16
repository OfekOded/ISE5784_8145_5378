package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * The Camera class represents a virtual camera used in a 3D rendering environment.
 * It defines the camera's location, orientation, and properties such as height, width, and distance.
 * The camera is responsible for constructing rays for rendering images.
 */
public class Camera implements Cloneable {

    private Point cameraLocation;
    private Vector Vup;
    private Vector Vto;
    private Vector Vright;
    private double height = 0;
    private double width = 0;
    private double VpDistance = 0;

    public static class Builder {
        final private Camera camera = new Camera();

        public Builder setCameraLocation(Point cameraLocation) {
            this.camera.cameraLocation = cameraLocation;
            return this;
        }

        public Builder setDirection(Vector vto, Vector vup) {
            if (isZero(vto.dotProduct(vup))) {
                this.camera.Vup = vup.normalize();
                this.camera.Vto = vto.normalize();
                return this;
            }
            throw new IllegalArgumentException("The input of the vto,vup variable is incorrect");
        }

        public Builder setVpSize(double height, double width) {
            if (height > 0) {
                this.camera.height = height;
            } else
                throw new IllegalArgumentException("The input of the height variable is incorrect");
            if (width > 0) {
                this.camera.width = width;
            } else
                throw new IllegalArgumentException("The input of the width variable is incorrect");
            return this;
        }

        public Builder setVpDistance(double distance) {
            if (distance > 0) {
                this.camera.VpDistance = distance;
                return this;
            }
            throw new IllegalArgumentException("The input of the distance variable is incorrect");
        }

        public Camera build() {
            String ERROR="Missing rendering data";
            if(isZero(camera.height))
                throw new MissingResourceException(ERROR,"Camera","height");
            if(isZero(camera.width))
                throw new MissingResourceException(ERROR,"Camera","width");
            if(isZero(camera.VpDistance))
                throw new MissingResourceException(ERROR,"Camera","VpDistance");
            if(camera.VpDistance<0)
                throw new IllegalArgumentException("The input of the distance variable is incorrect");
            if(camera.height<0)
                throw new IllegalArgumentException("The input of the height variable is incorrect");
            if(camera.width<0)
                throw new IllegalArgumentException("The input of the width variable is incorrect");
            this.camera.Vright=camera.Vto.crossProduct(camera.Vup).normalize();
            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Private constructor to enforce the use of the Builder pattern.
    private Camera() {}

    /**
     * Gets the height of the camera's view.
     *
     * @return The height of the camera's view.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets the width of the camera's view.
     *
     * @return The width of the camera's view.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the distance from the camera to the view plane.
     *
     * @return The distance from the camera to the view plane.
     */
    public double getVpDistance() {
        return VpDistance;
    }

    /**
     * Gets the location of the camera.
     *
     * @return The location of the camera.
     */
    public Point getCameraLocation() {
        return cameraLocation;
    }

    /**
     * Gets the up vector of the camera.
     *
     * @return The up vector of the camera.
     */
    public Vector getVup() {
        return Vup;
    }

    /**
     * Gets the towards vector of the camera.
     *
     * @return The towards vector of the camera.
     */
    public Vector getVto() {
        return Vto;
    }

    /**
     * Gets the right vector of the camera.
     *
     * @return The right vector of the camera.
     */
    public Vector getVright() {
        return Vright;
    }

    /**
     * Static method to obtain a new Builder for constructing a Camera.
     *
     * @return A new Builder for constructing a Camera.
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray for a given pixel in the image.
     *
     * @param nX The total number of pixels in the X direction.
     * @param nY The total number of pixels in the Y direction.
     * @param j  The pixel's X coordinate.
     * @param i  The pixel's Y coordinate.
     * @return The constructed ray for the specified pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //Image center
        Point Pc=cameraLocation.add(Vto.scale(VpDistance));
        //Ratio
        double Ry=height/nY;
        double Rx=width/nX;
    }
}