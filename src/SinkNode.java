/**
 * Created by jun on 6/6/17.
 */
import java.util.ArrayList;
import java.util.List;

public class SinkNode {
    private double X; // current x coordinates
    private double Y; // current y coordinates
    private double VOI;
    private double timeDelay;
    private int numberEventsCollected;
    private int numEventsSensed;
    private Queue<Grid> path;
    private double speed;
    public int flyTime = 0;

    SinkNode() {
        this.X = 0.0;
        this.Y = 0.0;
        this.VOI = 0.0;
        this.timeDelay = 0.0;
        this.numberEventsCollected = 0;
        this.numEventsSensed = 0;
        this.path = new Queue<Grid>();
        this.speed = 50.0;
    }

    public double getX() { return X; }
    public double getY() { return Y; }
    public void setX(double X) { this.X = X; }
    public void setY(double Y) { this.Y = Y; }

    public double getSpeed() { return speed; }

    public double getTimeDelay() { return timeDelay; }

    public int getNumEventsSensed() { return numEventsSensed; }
    public void addNumEventsSensed() {this.numEventsSensed += 1;}

    public int getNumEventsCollected() { return numberEventsCollected; }
//    public void setNumEventsCollected(int newValue) {this.numberEventsCollected = newValue;}


    public void update (double X, double Y, double VOI, Grid grid, int numEventsCollected, double timeDelay)
    {
        this.X = X;
        this.Y = Y;
        this.VOI += VOI;
        this.timeDelay += timeDelay;
        this.path.enqueue(grid); // add this grid to path
        this.numberEventsCollected += numEventsCollected;

    }

    public void updateQvalues_onPath (double INIT_REWARDS, double alpha, double discount)
    {
        List<Grid> pathList = path.toList();
        int index = pathList.size() - 1;
        while(index > 0) {
            Grid curr = pathList.get(index);
            Grid prev = pathList.get(index-1);
            int neigh_index = prev.getNeighbors().indexOf(curr);
            double maxQvalue = getMaxQvalue(curr);
            double newQvalue = 0.0;
            double oldQvalue = prev.getQvalues()[neigh_index];
            newQvalue = oldQvalue + alpha * (INIT_REWARDS + discount * maxQvalue - oldQvalue);
            prev.setQvalues(neigh_index, newQvalue);

            index--;
        }
    }


    public double getVOI() {
        return this.VOI;
    }

    public Queue<Grid> getPath() {
        return path;
    }

    public double getMaxQvalue(Grid curr) {
        double Q[] = curr.getQvalues();
        int numDirections = curr.getNeighbors().size();
        int maxIndex = 0;
        double maxNumber = Q[maxIndex];
        for(int i = 0; i < numDirections; i++) {
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
        int numDirections = current.getNeighbors().size();
        double number = Q[0];
        for(int i = 0; i < numDirections; i++) {
            if(number < Q[i]) {
                number = Q[i];
                index = i;
            }
        }
        // do random selection if Q values in all actions are 0.0
        if(index == 0 && Q[0] == 0.0) {
            index = (int)(Math.random() * (numDirections - 0));
        }
        return current.getNeighbors().get(index);
    }


    public Grid nextGrid_probability() {

        Grid current = this.getCurrentGrid();
        double[] Q = current.getQvalues();
        int numDirections = current.getNeighbors().size();
        int[] ranges = new int[numDirections];
        int sum = 0;

        for (int i = 0; i < numDirections; i++) {
            ranges[i] = (int)(Math.max(Q[i],1.0) * 10);
            sum += ranges[i];
        }

        int index = 0;
        int ran;
        if(sum == 0) { ran = 0; }
        else ran = (int)(Math.random() * ((sum - 0) + 1));

        if(ran == 0) { index = (int)(Math.random() * numDirections); }
        else {
            while(ran > 0) {
                ran -= ranges[index];
                index++;
            }
            index--;
        }
        return current.getNeighbors().get(index);
    }
}
