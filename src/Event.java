import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by jun on 6/6/17.
 */
public class Event {

    public static final double INITIAL_REWARD = 10.0;
    public static final double IS_EVENT_AGAIN_TIME = 24;
    public static final double EVENT_VALID_TIME = 144;

    private double init_rewards;
    private int start_round;
    private int collect_round;
    private AnimalTrace animal_trace;

    public Event(AnimalTrace animal_trace, int start_round, double INITIAL_REWARD) {
        this.start_round = start_round;
        this.init_rewards = INITIAL_REWARD;
        this.animal_trace = animal_trace;
        this.collect_round = start_round + (int)EVENT_VALID_TIME;
    }

    public double getInitReward() {
        return init_rewards;
    }
    public int getStartRound() {
        return start_round;
    }
    public AnimalTrace getAnimalTrace() {
        return animal_trace;
    }
    public String getAnimalID() {
        return this.getAnimalTrace().getAnimalID();
    }
    public double getX() {
        return this.getAnimalTrace().getX();
    }
    public double getY() {
        return this.getAnimalTrace().getY();
    }
    public int getCollectRound() {
        return collect_round;
    }
    public void setCollectRound(int collect_round) {
        this.collect_round = collect_round;
    }
}
