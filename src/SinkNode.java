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
        this.path = new Queue<Grid>(5);
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

    public void updateQvaluesMDPonPath (double INIT_REWARDS, double alpha, double discount)
    {
        List<Grid> pathList = path.toList();
        int index = pathList.size() - 1;
        while(index > 0) {
            Grid curr = pathList.get(index);
            Grid prev = pathList.get(index-1);
            int neigh_index = prev.getNeighbors().indexOf(curr);
            double maxQvalue = getMaxQvalue(curr);
            double oldQvalue = prev.getQvalues()[neigh_index];

            double dis = Math.pow(0.2, pathList.size() - 1 - index);

            double newQvalue = oldQvalue + dis * alpha * (INIT_REWARDS + discount * maxQvalue - oldQvalue);
            prev.setQvalues(neigh_index, newQvalue);

            index--;
        }
    }

    public void updateQvaluesMDP_E (double INIT_REWARDS, double alpha, double discount)
    {
        List<Grid> pathList = path.toList();
        int index = pathList.size() - 1;

        if(index > 0) {
            Grid curr = pathList.get(index);
            Grid prev = pathList.get(index-1);

            int neigh_index = prev.getNeighbors().indexOf(curr);
            double maxQvalue = getMaxQvalue(curr);
            double oldQvalue = prev.getQvalues()[neigh_index];


            double newQvalue = oldQvalue + alpha * (INIT_REWARDS + discount * maxQvalue - oldQvalue);
            prev.setQvalues(neigh_index, newQvalue);
        }
    }

//    public void updateGreedy (double REWARDS, int grid_index, double[] greedyValues)
//    {
//
//        List<Grid> pathList = path.toList();
//        int index = pathList.size() - 1;
//
//        if(REWARDS == 0) {
//            greedyValues[grid_index] = Math.max(10.0, (REWARDS + ));
//        }
//
//        if(index == 0) { return; }
//
//        Grid curr = pathList.get(index);
//        Grid prev = pathList.get(index-1);
//        int neigh_index = prev.getNeighbors().indexOf(curr);
//
//        double maxQvalue = getMaxQvalue(curr);
//        double oldQvalue = prev.getQvalues()[neigh_index];
//
//        double newQvalue = oldQvalue + alpha * (REWARDS + discount * maxQvalue - oldQvalue);
//        prev.setQvalues(neigh_index, newQvalue);
//
//    }


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

    public Grid nextGrid_Greedy() {
        Grid current = this.getCurrentGrid();

        Grid max = current;

        int numDirections = current.getNeighbors().size();

        for(int i = 0; i < numDirections; i++) {
            if(current.getNeighbors().get(i).greedyValue > max.greedyValue) {
                max = current.getNeighbors().get(i);
            }
        }

        if(max.greedyValue == 0.0) {
            int index = (int)(Math.random() * numDirections);
            return current.getNeighbors().get(index);
        }

        return max;
    }

    public Grid nextGrid_maxQvalueMDP(int percent) {
        Grid current = this.getCurrentGrid();
        int index = 0;
        double[] Q = current.getQvalues();
        int numDirections = current.getNeighbors().size();

        int ran = (int)(Math.random() * 100) + 1;

        if(ran <= percent) {
            index = (int)(Math.random() * numDirections);
            return current.getNeighbors().get(index);
        }

        double number = Q[0];
        for(int i = 0; i < numDirections; i++) {
            if(number < Q[i]) {
                number = Q[i];
                index = i;
            }
        }
        // do random selection if Q values in all actions are 0.0
        if(index == 0 && Q[0] == 0.0) {
            index = (int)(Math.random() * numDirections);
        }
        return current.getNeighbors().get(index);
    }

    public Grid nextGrid_Random(List<Grid> GRID_LIST) {

        int length = GRID_LIST.size();
        int index = (int)(Math.random() * length);
        return GRID_LIST.get(index);



//        Grid current = this.getCurrentGrid();
//        int index = 0;
//
//        int numDirections = current.getNeighbors().size();
//
//        index = (int)(Math.random() * numDirections);

//        return current.getNeighbors().get(index);
    }

    public Grid nextGrid_probability() {

        Grid current = this.getCurrentGrid();
        double[] Q = current.getQvalues();
        int numDirections = current.getNeighbors().size();
        int[] ranges = new int[numDirections];
        int sum = 0;

        for (int i = 0; i < numDirections; i++) {
            ranges[i] = (int)(Math.max(Q[i],0.2) * 10);
            sum += ranges[i];
        }

        int index = 0;
        int ran;
        ran = (int)(Math.random() * sum);

        while(ran >= 0) {
            ran -= ranges[index];
            index++;
        }
        index--;

        return current.getNeighbors().get(index);
    }
}
