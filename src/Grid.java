//import java.util.ArrayList;
import java.util.List;

public class Grid {
    private static final int NUM_DIRECTIONS = 9;
    private String name;
    private double headX;
    private double headY;
    private double[] Qvalues;
    private List<Grid> neighbors;

    Grid(String name, double x, double y) {
        this.name = name;
        this.headX = x;
        this.headY = y;
        this.Qvalues = new double[NUM_DIRECTIONS];
        this.neighbors = null;
    }

    public String getGridName() {
        return name;
    }
    public double getxCoordinate() {
        return headX;
    }
    public double getyCoordinate() {
        return headY;
    }

    public double[] getQvalues() {
        return Qvalues;
    }
    public void setQvalues(int index, double newValue) {
        this.Qvalues[index] = newValue;
    }


    public List<Grid> getNeighbors() {
        return neighbors;
    }
    public void setNeighbors(List<Grid> neighbors) {
        this.neighbors = neighbors;
    }
}
