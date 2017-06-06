import java.util.Date;


public class Trajectory{
    String animalID;
    double xCoordinate;
    double yCoordinate;
    Date date;
    public Trajectory(String animalID, double xCoordinate, double yCoordinate,
                      Date date) {
//        super();
        this.animalID = animalID;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.date = date;
    }

    public String getAnimalID() {
        return animalID;
    }
    public double getxCoordinate() {
        return xCoordinate;
    }
    public double getyCoordinate() {
        return yCoordinate;
    }
    public Date getDate() {
        return date;
    }

}