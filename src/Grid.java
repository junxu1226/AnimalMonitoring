import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Grid {
    private static final int NUM_DIRECTIONS = 9;
    private static final double DROP_EVENT_REWARD_LESS_THAN = 0.5;
    private static final double IS_EVENT_TIME_INTERVAL = 50;

    private String name;
    private double headX;
    private double headY;
    private double[] Qvalues;
    private List<Grid> neighbors;
//    private Date currentTime;
    private List<Event> eventList;

    Grid(String name, double x, double y) {
        this.name = name;
        this.headX = x;
        this.headY = y;
        this.Qvalues = new double[NUM_DIRECTIONS];
        this.neighbors = null;
        this.eventList = new ArrayList<Event>();
    }

    public String getGridName() {
        return name;
    }
    public double getX() {
        return headX;
    }
    public double getY() {
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
    public void setNeighbors(List<Grid> neighbors) { this.neighbors = neighbors; }

    public void addEvent(String animalID, int current_round) {

        if(isEvent(animalID, current_round)) {
            eventList.add(new Event(animalID, current_round, 10.0));
        }
    }

    private boolean isEvent(String animalID, int current_round) {

        int i = 0;
        while(i < eventList.size()) {
            Event e = eventList.get(i);
            if(animalID.equals(e.getAnimalID()) && (current_round - e.getStartRound()) > IS_EVENT_TIME_INTERVAL) {
                return true;
            }
            i++;
        }
        return false;
    }

    public double[] collectEvents(int current_round) {  // VOI =  Init * e^{-B * t}
        double[] events = new double[5];

        int num_events_collected = 0;
        double rewards = 0.0;
        double init_rewards = 0.0;
        double timeDelay = 0.0;
        double B_parameter = 0.1 / 6.0;


        int i = 0;
        while(i < eventList.size()) {
            init_rewards += eventList.get(i).getInitReward();
            double eachTimeDelay = current_round - eventList.get(i).getStartRound();
            double eachReward = eventList.get(i).getInitReward() *
                    Math.pow(Math.E, (current_round - eventList.get(i).getStartRound()) * B_parameter);
            i++;
            if(eachReward < DROP_EVENT_REWARD_LESS_THAN) { continue; }
            else {
                rewards += eachReward;
                num_events_collected++;
                timeDelay += eachTimeDelay;

            }
        }
        events[0] = init_rewards;
        events[1] = rewards;
        events[2] = eventList.size();
        events[3] = num_events_collected;
        events[4] = timeDelay;

        return events;
    }

    public void clearEvents() { eventList.clear(); }
}
