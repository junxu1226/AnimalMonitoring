import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Animal{
    private String animalID;
    private List<AnimalPoint> trajectory;

    public Animal(String animalID) {

        this.animalID = animalID;
        this.trajectory = new ArrayList<AnimalPoint>();
    }

    class AnimalPoint {
        String animalID;
        double X;
        double Y;
        Date date;
        public AnimalPoint(String animalID, double X, double Y, Date date) {
            this.animalID = animalID;
            this.X = X;
            this.Y = Y;
            this.date = date;
        }
    }
    public String getAnimalID() {
        return animalID;
    }
    public void addPoint(AnimalPoint point) {
        this.trajectory.add(point);
    }
    public List<AnimalPoint> getTrajectory() { return this.trajectory; }
    public AnimalPoint getPoint() { return this.trajectory.get(0); }
    public void removePoint() {
        this.trajectory.remove(0);
    }

}