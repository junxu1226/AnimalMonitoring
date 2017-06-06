/**
 * Created by jun on 6/6/17.
 */
public class SinkNode {
    private double X; // current x coordinates
    private double Y; // current y coordinates
    private double VOI;
    private double timeDelay;
    private int numberZebras;
    private int numberEvents;
    private Queue<Grid> path;

    SinkNode() {
        this.X = 0.0;
        this.Y = 0.0;
        this.VOI = 0.0;
        this.timeDelay = 0.0;
        this.numberZebras = 0;
        this.numberEvents = 0;
        this.path = new Queue<Grid>();
    }

    public double getX() {
        return X;
    }
    public double getY() {
        return Y;
    }
    public void updateXY (double X, double Y)
    {
        this.X = X;
        this.Y = Y;
    }


    public double getVOI() {
        return VOI;
    }
    public void updateVOI(double values) {
        VOI = VOI + values;
    }

    public Queue<Grid> getPath() {
        return path;
    }
    public void updatePath(Grid new_grid) {
        path.enqueue(new_grid);
    }

    public Grid getCurrentGrid() {
        return path.peek();
    }


    public Grid nextGrid_maxQvalue() {
        Grid current = this.getCurrentGrid();
        int index = 0;
        double[] Q = current.getQvalues();
        double number = Q[0];
        for(int i = 0; i < Q.length; i++) {
            if(number < Q[i]) {
                index = i;
            }
        }
        return current.getNeighbors().get(index);
    }


    public Grid nextGrid_probabilityt() {

        Grid current = this.getCurrentGrid();
        double[] Q = current.getQvalues();
        int[] ranges = new int[Q.length];
        int sum = 0;

        for (int i = 0; i < Q.length; i++) {
            ranges[i] = (int)(Q[i] * 100);
            sum += ranges[i];
        }

        int index = 0;
        int ran = 0;
        if(sum == 0) {
            ran = 0;
        }
        else ran = (int)(Math.random() * ((sum - 0) + 1));

        if(ran == 0) {
            return current.getNeighbors().get(index);
        }
        while(ran > 0) {
            ran -= ranges[index];
            index++;
        }
        return current.getNeighbors().get(index-1);
    }
}
