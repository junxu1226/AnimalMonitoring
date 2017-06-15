import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by jun on 6/14/17.
 */
public class Reader_Vultures {
    List<Animal> animalList;
    Animal A614 = new Animal("614");
    Animal A652 = new Animal("652");
    Animal A653 = new Animal("653");
    Animal A656 = new Animal("656");
    Animal A657 = new Animal("657");
    Animal A659 = new Animal("659");
    Animal A660 = new Animal("660");

//    Date theBeginTime = new Date(110, 11, 01, 00, 00, 00);
//    Date theEndTime = new Date(109, 11, 8, 00, 00, 00);

    public Reader_Vultures() {
        File f = new File("./data/test1.txt"); // vultures
        this.animalList = new ArrayList<>();
        this.animalList.add(A614);
        this.animalList.add(A652);
        this.animalList.add(A653);
        this.animalList.add(A656);
        this.animalList.add(A657);
        this.animalList.add(A659);
        this.animalList.add(A660);

        try {
            Scanner s = new Scanner(f);
//            System.out.println(s.);
            int num_traces = 0;
//            System.out.print(theBeginTime);
            while(s.hasNext()){

                int year = s.nextInt(); int month = s.nextInt(); int day = s.nextInt();
                int hours = s.nextInt(); int minutes = s.nextInt(); s.next();

                double yCoordinate = s.nextDouble();
                double xCoordinate = s.nextDouble();
                String animalID= s.next();
//                month = 1;
                Date d = new Date(year-1900, month-1, day, hours, minutes);

//                if(d.before(theBeginTime)||d.after(theEndTime)) { continue; }


                    xCoordinate = (xCoordinate - (-28)) * 1000.0 / 10;
                    yCoordinate = (yCoordinate - (14)) * 1000.0 / 5;

//                    System.out.print("ReadinTime is " + d + " " + animalID + '\n');
                    AnimalTrace trace = new AnimalTrace (animalID, xCoordinate, yCoordinate, d);
                    int i = 0;
                    while(i < animalList.size()) {
                        if(animalID.equals(animalList.get(i).getAnimalID())){
                            animalList.get(i).addTrace(trace);
//                            System.out.print(trace.getAnimalID() + " " + trace.getX() + " " + trace.getY() + " " + trace.getDate() + '\n');
                            break;
                        }
                        i++;
                    }
                    num_traces++;

            }

//            System.out.print("Total num of traces: " + num_traces + '\n');
            System.out.print("A614 is: " + animalList.get(0).getTrajectory().size() + '\n');
            System.out.print("A656 is: " + animalList.get(1).getTrajectory().size() + '\n');
//            System.out.print("FC is: " + animalList.get(2).getTrajectory().size() + '\n');
//            System.out.print("FD is: " + animalList.get(3).getTrajectory().size() + '\n');
            System.out.print("The size is: " + animalList.size() + '\n');
            s.close();
        } catch (IOException e) {}
    }
}
