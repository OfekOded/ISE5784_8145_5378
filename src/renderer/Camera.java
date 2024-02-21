package renderer;

import primitives.*;
import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * The Camera class represents a virtual camera used in a 3D rendering environment.
 * It defines the camera's location, orientation, and properties such as height, width, and distance.
 * The camera is responsible for constructing rays for rendering images.
 */
public class Camera implements Cloneable {
    Grid grid;
    boolean AntiAliasing =false;
    private Point cameraLocation;
    private Vector Vup;
    private Vector Vto;
    private Vector Vright;
    private double height = 0;
    private double width = 0;
    private double VpDistance = 0;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;



    /**
     * The Builder class for constructing a Camera object.
     */
    public static class Builder {
        final private Camera camera = new Camera();
        private Point Pto = null;
        public Builder setAntiAliasing(boolean antiAliasing) {
            camera.AntiAliasing = antiAliasing;
            return this;
        }
        /**
         * Sets the location of the camera.
         *
         * @param cameraLocation The location of the camera.
         * @return This Builder instance.
         */
        public Builder setCameraLocation(Point cameraLocation) {
            this.camera.cameraLocation = cameraLocation;
            if(Pto!=null)
                camera.Vto = Pto.subtract(camera.cameraLocation).normalize();
            return this;
        }

        /**
         * Sets the direction vectors of the camera.
         *
         * @param vto The towards vector.
         * @param vup The up vector.
         * @return This Builder instance.
         * @throws IllegalArgumentException If the input vectors are not perpendicular.
         */
        public Builder setDirection(Vector vto, Vector vup) {
            if (isZero(vto.dotProduct(vup))) {
                this.camera.Vup = vup.normalize();
                this.camera.Vto = vto.normalize();
                return this;
            }
            throw new IllegalArgumentException("The input of the vto,vup variable is incorrect");
        }

        /**
         * Set the direction of the camera
         * @param Pto point to
         * @param vup vector up
         * @return this object according to the builder design pattern
         */
        public Builder setDirection(Point Pto, Vector vup) {
            camera.Vup = vup;
            this.Pto = Pto;
            if(camera.cameraLocation!=null)
                camera.Vto = Pto.subtract(camera.cameraLocation).normalize();
            return this;
        }

        /**
         * Set the ImageWriter of the object camera
         * @param ImageWriter
         * @return this object according to the builder design pattern
         */
        public Builder setImageWriter(ImageWriter ImageWriter) {
            camera.imageWriter = ImageWriter;
            return this;
        }

        /**
         * Set the RayTracerBase of the object camera
         * @param RayTracerBase
         * @return this object according to the builder design pattern
         */
        public Builder setRayTracer(RayTracerBase RayTracerBase) {
            camera.rayTracer = RayTracerBase;
            return this;
        }

        /**
         * Sets the size of the view plane.
         *
         * @param height The height of the view plane.
         * @param width  The width of the view plane.
         * @return This Builder instance.
         * @throws IllegalArgumentException If the input height or width is not positive.
         */
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

        /**
         * Sets the distance from the camera to the view plane.
         *
         * @param distance The distance from the camera to the view plane.
         * @return This Builder instance.
         * @throws IllegalArgumentException If the input distance is not positive.
         */
        public Builder setVpDistance(double distance) {
            if (distance > 0) {
                this.camera.VpDistance = distance;
                return this;
            }
            throw new IllegalArgumentException("The input of the distance variable is incorrect");
        }

        public Builder setGrid(int rootNumberOfRays) {
            camera.grid = new Grid(rootNumberOfRays, camera.width/camera.imageWriter.getNx(), camera.height/camera.imageWriter.getNy());
            return this;
        }

        /**
         * Builds the Camera object.
         *
         * @return The constructed Camera object.
         * @throws MissingResourceException If essential rendering data is missing.
         * @throws IllegalArgumentException If height, width, or VpDistance is not positive.
         */
        public Camera build() {
            String ERROR = "Missing rendering data";
            if (Pto == null && camera.Vto == null)
                throw new MissingResourceException(ERROR, "Camera", "Vto/Pto");
            if (isZero(camera.height))
                throw new MissingResourceException(ERROR, "Camera", "height");
            if (isZero(camera.width))
                throw new MissingResourceException(ERROR, "Camera", "width");
            if (isZero(camera.VpDistance))
                throw new MissingResourceException(ERROR, "Camera", "VpDistance");
            if (camera.VpDistance < 0)
                throw new IllegalArgumentException("The input of the distance variable is incorrect");
            if (camera.height < 0)
                throw new IllegalArgumentException("The input of the height variable is incorrect");
            if (camera.width < 0)
                throw new IllegalArgumentException("The input of the width variable is incorrect");
            if (camera.rayTracer == null)
                throw new MissingResourceException(ERROR, "Camera", "rayTracer");
            if (camera.imageWriter == null)
                throw new MissingResourceException(ERROR, "Camera", "imageWriter");
            if (Pto != null) {
                if (Pto.equals(camera.cameraLocation))
                    throw new IllegalArgumentException("must be different");
            if(Pto.subtract(camera.cameraLocation).normalize().equals(this.camera.Vup))
                    throw new IllegalArgumentException("must be different");
            this.camera.Vright = camera.Vup.crossProduct(Pto.subtract(camera.cameraLocation)).normalize();
            } else
                this.camera.Vright = camera.Vup.crossProduct(camera.Vto).normalize();
            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Private constructor to enforce the use of the Builder pattern.
    private Camera() {
    }

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
        // Image center
        Point centerPoint = getCenterPoint(nX, nY, j, i);
        return new Ray(cameraLocation, centerPoint.subtract(cameraLocation));
    }

    private Point getCenterPoint(int nX, int nY, int j, int i) {
        Point imageCenter = cameraLocation.add(Vto.scale(VpDistance));

        // Pixel aspect ratio
        double pixelHeight = height / nY;
        double pixelWidth = width / nX;

        // Calculate pixel coordinates in camera space
        double yOffset = -(i - (double) (nY - 1) / 2) * pixelHeight;
        double xOffset = (j - (double) (nX - 1) / 2) * pixelWidth;

        Point centerPoint;

        // Handle special cases where one of the offsets is zero
        if (xOffset == 0 && yOffset == 0)
            centerPoint = imageCenter;
        else if (xOffset == 0)
            centerPoint = imageCenter.add(Vup.scale(yOffset));
        else if (yOffset == 0)
            centerPoint = imageCenter.add(Vright.scale(xOffset));
        else
            centerPoint = imageCenter.add(Vright.scale(xOffset).add(Vup.scale(yOffset)));
        return centerPoint;
    }

    /**
     * Renders the image by casting rays for each pixel and tracing them through the scene.
     *
     * @return This Camera instance after rendering the image.
     */
    public Camera renderImage() {
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                this.castRay(imageWriter.getNx(), imageWriter.getNy(), i, j);
            }
        }
        return this;
    }

    /**
     * Prints a grid on the image to visualize pixel alignment.
     *
     * @param interval The interval between grid lines.
     * @param color    The color of the grid lines.
     * @return This Camera instance after printing the grid.
     */
    public Camera printGrid(int interval, Color color) {
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % interval == 0 || i == imageWriter.getNx() - 1 || j % interval == 0 || j == imageWriter.getNy() - 1)
                    imageWriter.writePixel(i, j, color);
            }
        }
        return this;
    }

    /**
     * Writes the rendered image to a file.
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

    /**
     * Casts a ray for a given pixel in the image and traces it through the scene.
     *
     * @param Nx The total number of pixels in the X direction.
     * @param Ny The total number of pixels in the Y direction.
     * @param i  The pixel's X coordinate.
     * @param j  The pixel's Y coordinate.
     */
    private void castRay(int Nx, int Ny, int i, int j) {
        Color averageOfColors=Color.BLACK;
        if(!AntiAliasing)
            imageWriter.writePixel(i, j, rayTracer.traceRay(constructRay(Nx, Ny, i, j)));
        else{
            grid.rayBeam(getCenterPoint(Nx,Ny,i,j),Vup,Vto);
            for(Point point: grid.grid) {
                averageOfColors=averageOfColors.add(rayTracer.traceRay( new Ray(cameraLocation, point.subtract(cameraLocation))));
            }
            imageWriter.writePixel(i,j,averageOfColors.scale((double) 1 / (grid.rootNumberOfRays * grid.rootNumberOfRays)));
        }
    }

}
