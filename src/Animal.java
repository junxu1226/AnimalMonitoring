import java.util.ArrayList;
import java.util.List;


public class Animal{
    private String animalID;
    private List<AnimalTrace> trajectory;

    public Animal(String animalID) {

        this.animalID = animalID;
        this.trajectory = new ArrayList<AnimalTrace>();
    }

    public String getAnimalID() {
        return animalID;
    }
    public void addTrace(AnimalTrace trace) {
        this.trajectory.add(trace);
    }
    public List<AnimalTrace> getTrajectory() { return this.trajectory; }
    public AnimalTrace getLastTrace() { return this.trajectory.get(0); }
    public void removeLastTrace(AnimalTrace trace) {
        this.trajectory.remove(trace);
    }

}