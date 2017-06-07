import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by jun on 6/6/17.
 */
public class Event {

//    private static final double INITIAL_REWARD = 10.0;
    private double init_rewards;
    private int start_round;
    private String animalID;
    public Event(String animalID, int start_round, double INITIAL_REWARD) {
        this.start_round = start_round;
        this.init_rewards = INITIAL_REWARD;
        this.animalID = animalID;
    }

    public double getInitReward() {
        return init_rewards;
    }
    public int getStartRound() {
        return start_round;
    }
    public String getAnimalID() {
        return animalID;
    }
}
