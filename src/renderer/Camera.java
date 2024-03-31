package renderer;

import primitives.*;
import scene.Scene;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.IntStream;

import static primitives.Util.isZero;

/**
 * The Camera class represents a virtual camera used in a 3D rendering environment.
 * It defines the camera's location, orientation, and properties such as height, width, and distance.
 * The camera is responsible for constructing rays for rendering images.
 */
public class Camera implements Cloneable {
    int rootNumberOfRays = 1;
    Grid grid;
    boolean adaptiveSuperSampling = false;
    boolean AntiAliasing = false;
    private Point cameraLocation;
    private Vector Vup;
    private Vector Vto;
    private Vector Vright;
    private double height = 0;
    private double width = 0;
    private double VpDistance = 0;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    //  private PixelManager PixelManager = new PixelManager();


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

        public Builder adaptiveSuperSampling(boolean adaptiveSuperSampling) {
            camera.adaptiveSuperSampling = adaptiveSuperSampling;
            return this;
        }

        public Builder rootNumberOfRays(int rootNumberOfRays) {
            camera.rootNumberOfRays = rootNumberOfRays;
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
            if (Pto != null)
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
         *
         * @param Pto point to
         * @param vup vector up
         * @return this object according to the builder design pattern
         */
        public Builder setDirection(Point Pto, Vector vup) {
            camera.Vup = vup;
            this.Pto = Pto;
            if (camera.cameraLocation != null)
                camera.Vto = Pto.subtract(camera.cameraLocation).normalize();
            return this;
        }

        /**
         * Set the ImageWriter of the object camera
         *
         * @param ImageWriter
         * @return this object according to the builder design pattern
         */
        public Builder setImageWriter(ImageWriter ImageWriter) {
            camera.imageWriter = ImageWriter;
            return this;
        }

        /**
         * Set the RayTracerBase of the object camera
         *
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
            this.camera.rootNumberOfRays = rootNumberOfRays;
            camera.grid = new Grid(rootNumberOfRays, camera.width / camera.imageWriter.getNx(), camera.height / camera.imageWriter.getNy());
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
                if (Pto.subtract(camera.cameraLocation).normalize().equals(this.camera.Vup))
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

    private Point getCenterPoint(int nX, int nY, double j, double i) {
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
        Color averageOfColors = Color.BLACK;
        if (!AntiAliasing && !adaptiveSuperSampling)
            imageWriter.writePixel(i, j, rayTracer.traceRay(constructRay(Nx, Ny, i, j)));
        else if (AntiAliasing && adaptiveSuperSampling) {
            ArrayList<ColorPoint> colorPoints = new ArrayList<>();
            List<Point> squareGrid = generateSquareGridPoints(rootNumberOfRays, width / Nx, getCenterPoint(Nx, Ny, i,j));
            for (int k = 0; k < rootNumberOfRays * rootNumberOfRays; k++) {
                colorPoints.add(new ColorPoint(squareGrid.get(k), null));
            }
            imageWriter.writePixel(i, j, adaptiveSuperSampling(colorPoints, rootNumberOfRays, 0, 0));
        } else if (!adaptiveSuperSampling) {
            grid.rayBeam(getCenterPoint(Nx, Ny, i, j), Vup, Vto);
            for (Point point : grid.grid) {
                averageOfColors = averageOfColors.add(rayTracer.traceRay(new Ray(cameraLocation, point.subtract(cameraLocation))));
            }
            imageWriter.writePixel(i, j, averageOfColors.scale((double) 1 / (grid.rootNumberOfRays * grid.rootNumberOfRays)));
        }
        Pixel.pixelDone();
    }

    private int threadsCount = 0;
    final int SPARE_THREADS = 2;
    double printInterval = 0;


    public Camera setMultithreading(int threads) {
        if (threads < -2) throw new IllegalArgumentException("Multithreading must be -2 or higher");
        if (threads >= -1) threadsCount = threads;
        else { // == -2
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    public Camera setDebugPrint(double interval) {
        printInterval = interval;
        return this;
    }


    record Pixel(int row, int col) {
        private static int maxRows = 0;
        private static int maxCols = 0;
        private static long totalPixels = 0l;
        private static volatile int cRow = 0;
        private static volatile int cCol = -1;
        private static volatile long pixels = 0l;
        private static volatile int lastPrinted = 0;
        private static boolean print = false;
        private static long printInterval = 100l;
        private static final String PRINT_FORMAT = "%5.1f%%\r";
        private static Object mutexNext = new Object();
        private static Object mutexPixels = new Object();

        static void initialize(int maxRows, int maxCols, double interval) {
            Pixel.maxRows = maxRows;
            Pixel.maxCols = maxCols;
            Pixel.totalPixels = (long) maxRows * maxCols;
            printInterval = (int) (interval * 10);
            if (print = printInterval != 0) System.out.printf(PRINT_FORMAT, 0d);
        }


        static Pixel nextPixel() {
            synchronized (mutexNext) {
                if (cRow == maxRows) return null;
                ++cCol;
                if (cCol < maxCols) return new Pixel(cRow, cCol);
                cCol = 0;
                ++cRow;
                if (cRow < maxRows) return new Pixel(cRow, cCol);
            }
            return null;
        }

        static void pixelDone() {
            boolean flag = false;
            int percentage = 0;
            synchronized (mutexPixels) {
                ++pixels;
                if (print) {
                    percentage = (int) (1000l * pixels / totalPixels);
                    if (percentage - lastPrinted >= printInterval) {
                        lastPrinted = percentage;
                        flag = true;
                    }
                }
            }
            if (flag) System.out.printf(PRINT_FORMAT, percentage / 10d);
        }
    }

    public Camera renderImage() {
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        Pixel.initialize(nY, nX, printInterval);
        if (threadsCount == 0)
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j)
                    castRay(nX, nY, j, i);
        else if (threadsCount == -1) {
            IntStream.range(0, nY).parallel() //
                    .forEach(i -> IntStream.range(0, nX).parallel() //
                            .forEach(j -> castRay(nX, nY, j, i)));
        } else {
            var threads = new LinkedList<Thread>();
            while (threadsCount-- > 0)
                threads.add(new Thread(() -> {
                    Pixel pixel;
                    while ((pixel = Pixel.nextPixel()) != null)
                        castRay(nX, nY, pixel.col(), pixel.row());
                }));
            for (var thread : threads) thread.start();
            try {
                for (var thread : threads) thread.join();
            } catch (InterruptedException ignore) {
            }
        }
        return this;
    }

    public Color adaptiveSuperSampling(ArrayList<ColorPoint> calculatedPoints, int gridSideLength, int gridRow, int gridColumn) {
        List<Integer> cornerIndices = corners(gridSideLength, gridRow, gridColumn);
        boolean isUniformColor = true;
        for (int index = 0; index < cornerIndices.size(); index++) {
            if (calculatedPoints.get(cornerIndices.get(index)).color == null)
                calculatedPoints.set(cornerIndices.get(index), new ColorPoint(calculatedPoints.get(cornerIndices.get(index)).point, rayTracer.traceRay(new Ray(cameraLocation, calculatedPoints.get(cornerIndices.get(index)).point.subtract(cameraLocation)))));
            if (index != 0)
                if (!calculatedPoints.get(cornerIndices.get(index)).color.equals(calculatedPoints.get(cornerIndices.get(index - 1)).color)) {
                    isUniformColor = false;
                }
        }
        if (isUniformColor)
            return calculatedPoints.get(cornerIndices.get(0)).color;
        Color combinedColor = Color.BLACK;
        if (gridSideLength > 2)
            combinedColor = combinedColor.add(adaptiveSuperSampling(calculatedPoints, (gridSideLength+1)/ 2 , gridRow, gridColumn))
                    .add(adaptiveSuperSampling(calculatedPoints, (gridSideLength+1) /2 , gridRow + gridSideLength / 2, gridColumn))
                    .add(adaptiveSuperSampling(calculatedPoints, (gridSideLength+1) / 2 , gridRow, gridColumn + gridSideLength / 2))
                    .add(adaptiveSuperSampling(calculatedPoints, (gridSideLength+1) / 2, gridRow + gridSideLength / 2, gridColumn + gridSideLength / 2));
        else
            for (int i = 0; i < 4; i++)
                combinedColor = combinedColor.add(calculatedPoints.get(cornerIndices.get(i)).color);
        return combinedColor.reduce(4);
    }

    public List<Integer> corners(int gridSide, int initialRow, int initialColumn) {
        return List.of(
                initialRow + initialColumn * rootNumberOfRays,
                initialRow + gridSide - 1 + initialColumn * rootNumberOfRays,
                initialRow + (initialColumn + gridSide - 1) * rootNumberOfRays,
                initialRow + gridSide - 1 + (initialColumn + gridSide - 1) * rootNumberOfRays
        );
    }


    public List<Point> generateSquareGridPoints(int sampleCount, double gridSideLength, Point gridCenter) {
        double pixelStep = gridSideLength / sampleCount;
        double yPos, xPos;
        Point gridPoint;
        List<Point> pointsList = new ArrayList<>();
        for (int i = 0; i < sampleCount; i++) {
            for (int j = 0; j < sampleCount; j++) {
                yPos = (-(i - (sampleCount - 1.0) / 2.0) * pixelStep);
                xPos = ((j - (sampleCount - 1.0) / 2.0) * pixelStep);
                // gridPoint - center point in the sub-pixel i,j
                if (!Util.isZero(xPos) && !Util.isZero(yPos))
                    gridPoint = gridCenter.add(Vright.scale(xPos).add(Vup.scale(yPos)));
                else if (!Util.isZero(xPos))
                    gridPoint = gridCenter.add(Vright.scale(xPos));
                else if (!Util.isZero(yPos))
                    gridPoint = gridCenter.add(Vup.scale(yPos));
                else
                    gridPoint = gridCenter;

                pointsList.add(gridPoint);
            }
        }
        return pointsList;
    }
}


