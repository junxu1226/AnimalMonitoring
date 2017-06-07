import java.util.Date;
import java.util.List;


public class Animal{
    private String animalID;
    private List<AnimalPoint> trajectory;

    public Animal(String animalID) {
        this.animalID = animalID;
    }

    class AnimalPoint {
        double X;
        double Y;
        Date date;
        public AnimalPoint(double X, double Y, Date date) {
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