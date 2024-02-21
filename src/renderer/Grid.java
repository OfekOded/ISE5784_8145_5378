package renderer;

import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;
import static primitives.Util.random;


public class Grid {
    public int rootNumberOfRays = 1;
    public double width = 0;
    public double height = 0;

    public Grid(int rootNumberOfRays, double width, double height) {
        this.rootNumberOfRays = rootNumberOfRays == 0 ? 1 : rootNumberOfRays;
        this.width = width;
        this.height = height;
    }

    List<Point> grid;

    public void rayBeam(Point p, Vector vUp, Vector vRight) {
        if (rootNumberOfRays == 1)
            grid = List.of(p);
        else {
            grid = new LinkedList<Point>();
            // Pixel aspect ratio
            double rH = height / rootNumberOfRays;
            double rW = width / rootNumberOfRays;

            // Calculate pixel coordinates in camera space
            for (int i = 0; i < rootNumberOfRays; i++)
                for (int j = 0; j < rootNumberOfRays; j++) {
                    double yOffset = -(i - (double) (rootNumberOfRays - 1) / 2) * rH- rH / 2;
                    double xOffset = (j - (double) (rootNumberOfRays - 1) / 2) * rW - rW / 2;

                    double xMove = random(0, rW);
                    double yMove = random(0, rH);


                    Point point = p;

                    if (!isZero(xOffset)|| !isZero(xMove))
                        point=point.add(vRight.scale(xOffset  +xMove));
                    if (!isZero(yOffset) || !isZero(yMove))
                        point=point.add(vUp.scale(yOffset + yMove));
                        //point.add(vRight.scale(yOffset + yMove));

                    grid.addLast(point);
                }
        }
    }
}
