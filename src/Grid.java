import java.util.ArrayList;
import java.util.List;

public class Grid {
    public static final int NUM_DIRECTIONS = 9;
    String name;
    double headX;
    double headY;
    double[] Qvalues;
    List<Grid> neighbors;

    public Grid(String name, double x, double y) {

        headX = x;
        headY = y;
        Qvalues = new double[NUM_DIRECTIONS];
        neighbors = null;
    }
}
