import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Grid {
    private static final int NUM_DIRECTIONS = 9;
    private static final double DROP_EVENT_REWARD_LESS_THAN = 0.0;


    private String name;
    private double headX;
    private double headY;
    private double[] Qvalues = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}; // size: NUM_DIRECTIONS
    private List<Grid> neighbors;
//    private Date currentTime;
    private List<Event> eventList;

    Grid(String name, double x, double y) {
        this.name = name;
        this.headX = x;
        this.headY = y;
        this.neighbors = new ArrayList<>();
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

    public void addEvent(Event event) { eventList.add(event); }

    public boolean isEvent(Event event) {

        int i = 0;
        while(i < eventList.size()) {
            Event e = eventList.get(i);
            if(event.getAnimalID().equals(e.getAnimalID()) && (event.getStartRound() - e.getStartRound()) <= Event.IS_EVENT_TIME_INTERVAL) {
                return false;
            }
            i++;
        }
        return true;
    }

    public double[] collectEvents(int current_round) {  // VOI =  Init * e^{-B * t}
        double[] events = new double[5];

        int num_events_collected = 0;
        double rewards = 0.0;
        double init_rewards = 0.0;
        double timeDelay = 0.0;
        double B_parameter = 1.0 / 60.0;


        int i = 0;
        while(i < eventList.size()) {
            init_rewards += eventList.get(i).getInitReward();
            double eachReward = eventList.get(i).getInitReward() *
                    Math.pow(Math.E, -(current_round - eventList.get(i).getStartRound()) * B_parameter);

            if(eachReward < DROP_EVENT_REWARD_LESS_THAN) { continue; }
            double eachTimeDelay = current_round - eventList.get(i).getStartRound();
            rewards += eachReward;
            num_events_collected++;
            timeDelay += eachTimeDelay;

            i++;
        }
        events[0] = init_rewards;
        events[1] = rewards;
        events[2] = eventList.size();
        events[3] = num_events_collected;
        events[4] = timeDelay;

        return events;
    }

    public void clearEvents() { eventList.clear(); }
    public List<Event> getEventList() { return this.eventList; }
}
