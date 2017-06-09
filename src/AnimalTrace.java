import java.util.Date;

/**
 * Created by jun on 6/8/17.
 */

class AnimalTrace {
    private String animalID;
    private double X;
    private double Y;
    private Date date;
    public AnimalTrace(String animalID, double X, double Y, Date date) {
        this.animalID = animalID;
        this.X = X;
        this.Y = Y;
        this.date = date;
    }
    public String getAnimalID() { return this.animalID; }
    public double getX() { return this.X; }
    public double getY() {
        return this.Y;
    }
    public Date getDate() {
        return this.date;
    }
}