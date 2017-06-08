/**
 * Created by jun on 6/6/17.
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class File_Reader {


    List<Animal> animalList;
    Animal FA = new Animal("FA");
    Animal FB = new Animal("FB");

    Date theBeginTime = new Date(113, 01, 01, 00, 00, 00);
    Date theEndTime = new Date(113, 06, 5, 00, 00, 00);

    public File_Reader() {
        File f = new File("Leopard11.txt");
        try {
            Scanner s = new Scanner(f);
//            System.out.println("just for test");
            while(s.hasNextLine()){
                s.next();s.next();s.next();

                int year = s.nextInt(); int month = s.nextInt(); int day = s.nextInt();
                int hours = s.nextInt(); int minutes = s.nextInt();
                double xCoordinate = s.nextDouble();
                double yCoordinate = s.nextDouble();
                String animalID= s.next();
                Date d = new Date(year-1900, month-1, day, hours, minutes);

                if (d.before(theBeginTime)||d.after(theEndTime)){ continue; }
//                Animal.AnimalPoint point = new Animal.AnimalPoint(animalID, xCoordinate, yCoordinate, d);
                if (animalID.contentEquals("FA")){

                    xCoordinate = (xCoordinate - 21.820000) * (1000.00 / 0.09);
                    yCoordinate = (yCoordinate - 26.927000) * (1000.00 / 0.06);
                    FA.addPoint(FA.new AnimalPoint(animalID, xCoordinate, yCoordinate, d));
                }
                else if(animalID.contentEquals("FB")) {
                    xCoordinate = (xCoordinate - 21.800000)*(1000.00/0.34);
                    yCoordinate = (yCoordinate - 26.909000)*(1000.00/0.074);
                    FB.addPoint(FB.new AnimalPoint(animalID, xCoordinate, yCoordinate, d));
                }

            }

            s.close();
        } catch (IOException e) {}
        this.animalList = new ArrayList<>();
        this.animalList.add(FA);
        this.animalList.add(FB);
    }

}
