/**
 * Created by jun on 6/6/17.
 */
import java.util.List;

public class SinkNode {
    private double X; // current x coordinates
    private double Y; // current y coordinates
    private double VOI;
    private double timeDelay;
    private int numberZebras;
    private int numberEvents;
    private Queue<Grid> path;
    private double speed;

    SinkNode() {
        this.X = 0.0;
        this.Y = 0.0;
        this.VOI = 0.0;
        this.timeDelay = 0.0;
        this.numberZebras = 0;
        this.numberEvents = 0;
        this.path = new Queue<Grid>();
//        this.date = new Date(113, 01, 01, 00, 00, 00);
        this.speed = 15.0;
    }

    public double getX() {
        return X;
    }
    public double getY() {
        return Y;
    }
    public void setX(double X) {
        this.X = X;
    }
    public void setY(double Y) {
        this.Y = Y;
    }

    public double getSpeed() {
        return speed;
    }
    public void update (double X, double Y, double VOI, Grid grid)
    {
        this.X = X;
        this.Y = Y;
        this.VOI += VOI;
        this.path.enqueue(grid); // add this grid to path

    }

    public void updateQvalues_onPath (double INIT_REWARDS, double alpha, double discount)
    {
        List<Grid> pathList = (List) path;
        int index = pathList.size() - 1;
        while(index > 0) {
            Grid curr = pathList.get(index);
            Grid prev = pathList.get(index-1);
            int neigh_index = prev.getNeighbors().indexOf(curr);
            double maxQvalue = getMaxQvalue(curr.getQvalues());
            double newQvalue = 0.0;
            double oldQvalue = prev.getQvalues()[neigh_index];
            newQvalue = oldQvalue + alpha * (INIT_REWARDS + discount * maxQvalue - oldQvalue);
            prev.setQvalues(neigh_index, newQvalue);

            index--;
        }
    }


    public double getVOI() {
        return VOI;
    }

    public Queue<Grid> getPath() {
        return path;
    }

    public double getMaxQvalue(double[] Q) {
        int maxIndex = 0;
        double maxNumber = Q[maxIndex];
        for(int i = 0; i < Q.length; i++) {
            if(maxNumber < Q[i]) {
                maxIndex = i;
                maxNumber = Q[i];
            }
        }

        return maxIndex;
    }

    public Grid getCurrentGrid() {
        return path.peek_tail();
    }

    public Grid nextGrid_maxQvalue() {
        Grid current = this.getCurrentGrid();
        int index = 0;
        double[] Q = current.getQvalues();
        double number = Q[0];
        for(int i = 0; i < Q.length; i++) {
            if(number < Q[i]) {
                number = Q[i];
                index = i;
            }
        }
        // do random selection if Q values in all actions are 0.0
        if(index == 0 && Q[0] == 0.0) {
            index = (int)(Math.random() * (Q.length - 0));
        }
        return current.getNeighbors().get(index);
    }


    public Grid nextGrid_probability() {

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
