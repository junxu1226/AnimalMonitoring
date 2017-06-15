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
    Animal FC = new Animal("FC");
    Animal FD = new Animal("FD");
    Animal FE = new Animal("FE");
    Animal FF = new Animal("FF");
    Animal FG = new Animal("FG");
    Animal FH = new Animal("FH");
    Animal MA = new Animal("MA");
    Animal MB = new Animal("MB");

    Date theBeginTime = new Date(113, 02, 01, 00, 00, 00);
    Date theEndTime = new Date(113, 02, 30, 00, 00, 00);

    public File_Reader() {
        File f = new File("./data/pandas.txt"); // vultures
        this.animalList = new ArrayList<>();
        this.animalList.add(FA);
        this.animalList.add(FB);
        this.animalList.add(FC);
        this.animalList.add(FD);
        this.animalList.add(FE);
        this.animalList.add(FF);
        this.animalList.add(FG);
        this.animalList.add(FH);
        this.animalList.add(MA);
        this.animalList.add(MB);

        try {
            Scanner s = new Scanner(f);
//            System.out.println(s.);
            int num_traces = 0;
            while(s.hasNext()){

                s.next();s.next();s.next();

                int year = s.nextInt(); int month = s.nextInt(); int day = s.nextInt();
                int hours = s.nextInt(); int minutes = s.nextInt();
                double xCoordinate = s.nextDouble();
                double yCoordinate = s.nextDouble();
                String animalID= s.next();
//                month = 1;
                Date d = new Date(year-1900, month-1, day, hours, minutes);

                if(d.before(theBeginTime)||d.after(theEndTime)) { continue; }

                if(xCoordinate > 21.80 && xCoordinate < 21.87 && yCoordinate > 26.95 && yCoordinate < 27.00) {

                    xCoordinate = (xCoordinate - 21.80) * 1000.0 / 0.07;
                    yCoordinate = (yCoordinate - 26.95) * 1000.0 / 0.05;

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
            }

//            System.out.print("Total num of traces: " + num_traces + '\n');
            System.out.print("FA is: " + animalList.get(0).getTrajectory().size() + '\n');
            System.out.print("FB is: " + animalList.get(1).getTrajectory().size() + '\n');
            System.out.print("FC is: " + animalList.get(2).getTrajectory().size() + '\n');
            System.out.print("FD is: " + animalList.get(3).getTrajectory().size() + '\n');
            System.out.print("The size is: " + animalList.size() + '\n');
            s.close();
        } catch (IOException e) {}
    }
}
