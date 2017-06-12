/**
 * Created by jun on 6/6/17.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;
import java.util.Timer;


public class AnimalMonitoring extends JFrame {


    int GLOBAL_TIME_ROUNDs;  // silumation time in rounds
    Timer t = new Timer();


    List<Grid> GRID_LIST;
    List<Grid> ROUND_GRID_LIST;
    Grid NEXT_SINK_GRID; // set the first Grid "A1" as the next grid when simulation starts


    String Mode = "MDP-E";
    SinkNode SINK = new SinkNode();


    List<Animal> animalList;
    List<Event> eventPlotList;
    List<Event> allEventsRecorder;


    public AnimalMonitoring(List<Animal> animalList) {

        int num_grids_each_line = 4;
        InitialGrids grids = new InitialGrids(num_grids_each_line);
        this.GRID_LIST = grids.getGridList();
        this.ROUND_GRID_LIST = grids.getRoundGridList();
        this.NEXT_SINK_GRID = ROUND_GRID_LIST.get(0);


        this.animalList = animalList;
        this.eventPlotList = new ArrayList<>();
        this.allEventsRecorder = new ArrayList<>();


        NetworkPanel NMPanel = new NetworkPanel(SINK, eventPlotList);
        this.add(NMPanel);
        this.setBackground(Color.LIGHT_GRAY);
        this.createVisualizer(1500, 1000);
        this.setTitle("Max Value of Information on Animal Monitoring");



        Task task = new Task(this);
        t.schedule(task, 0, 100);
    }



    private void createVisualizer(int width, int height) {
        this.setSize(width, height);
        this.setVisible(true);
    }



    class Task extends TimerTask {
        AnimalMonitoring animalMonitor;
        Date theBeginTime = new Date(113, 02, 01, 00, 00, 00);
        long startTime = theBeginTime.getTime();
        long newTimeInMilliSeconds = startTime;
        double samplingTimeOfSimulation = 600; // in seconds

        int index = 0;
        int index_grid = 0;
        public Task(AnimalMonitoring model) {
            super();
            this.animalMonitor = model;
        }

        public void run() {
            GLOBAL_TIME_ROUNDs = index++;
            SINK.flyTime = GLOBAL_TIME_ROUNDs;
            newTimeInMilliSeconds = startTime + (long) samplingTimeOfSimulation
                    * 1000 * GLOBAL_TIME_ROUNDs;
            Date newDate = new Date(newTimeInMilliSeconds);

//            System.out.print("CurrentTime is " + newDate + '\n');

            //******************************* sensing animals ************************//
            int i = 0;
            while(i < animalList.size()) {

                if(animalList.get(i).getTrajectory().isEmpty()) {
                    i++;
                    continue;
                }

                AnimalTrace trace = animalList.get(i).getLastTrace();

                while( newDate.after(trace.getDate()) && (! animalList.get(i).getTrajectory().isEmpty()) ) {
                    animalList.get(i).removeLastTrace(trace);
                    trace = animalList.get(i).getLastTrace();
                }

                if(newDate.equals(trace.getDate())) {
                    Grid grid = findWhichGrid(trace.getX(), trace.getY(), GRID_LIST);
                    int current_round = GLOBAL_TIME_ROUNDs;
                    Event event = new Event(trace, current_round, Event.INITIAL_REWARD);

                    if(grid.isEvent(event)) {
                        grid.addEvent(event);
                        SINK.addNumEventsSensed();
                        allEventsRecorder.add(event);
                    }
                    animalList.get(i).removeLastTrace(trace);
                }

                i++;
            }

            //******************************* sensing animals ************************//

            double tmpDis_SINK = findDistanceBetweenTwoPoints(SINK.getX(), SINK.getY(),
                    NEXT_SINK_GRID.getX(), NEXT_SINK_GRID.getY());

            if(tmpDis_SINK > SINK.getSpeed()) {
                double Direc_X = (NEXT_SINK_GRID.getX() - SINK.getX())/tmpDis_SINK;
                double Direc_Y = (NEXT_SINK_GRID.getY() - SINK.getY())/tmpDis_SINK;
                SINK.setX(SINK.getX() + SINK.getSpeed()*Direc_X);
                SINK.setY(SINK.getY() + SINK.getSpeed()* Direc_Y);
            }
            else {
                //********************  Information collection and Q update ************//

                index_grid++;
                System.out.print("CurrentTime is " + newDate + " in rounds: " + GLOBAL_TIME_ROUNDs + '\n');

                int current_round = GLOBAL_TIME_ROUNDs;
                double[] events_info = NEXT_SINK_GRID.collectEvents(current_round);
                System.out.println("Events info: " + events_info[0] + " " + events_info[1] + " " + events_info[2] +
                        " " + events_info[3] + " " + events_info[4]);

                // **** events_info[] = [init_rewards, real_rewards, num_events_sensed, num_events_collected, timeDelay]

                NEXT_SINK_GRID.clearEvents();  // clear events in this Grid

                SINK.update(NEXT_SINK_GRID.getX(), NEXT_SINK_GRID.getY(),
                        events_info[1], NEXT_SINK_GRID, (int)events_info[3], events_info[4]);

                SINK.updateQvaluesMDPonPath(events_info[0], 0.5, 0.8);
                if(events_info[1] > 0) {NEXT_SINK_GRID.greedyValue = 25.0;}
                else NEXT_SINK_GRID.greedyValue = Math.max(NEXT_SINK_GRID.greedyValue - 1.0, 0.0);

                System.out.println("Q VALUES: " + Arrays.toString(NEXT_SINK_GRID.getQvalues()));

                // ******************* NEXT_SINK_GRID path planning ***********************//

                if(Mode.equals("MDP")) {
                    if( index_grid < ROUND_GRID_LIST.size() ) {
                        NEXT_SINK_GRID = ROUND_GRID_LIST.get(index_grid);
                    }
                    else NEXT_SINK_GRID = SINK.nextGrid_probability();
                }
                if(Mode.equals("MDP-E")) {
                    if( index_grid < ROUND_GRID_LIST.size() ) {
                        NEXT_SINK_GRID = ROUND_GRID_LIST.get(index_grid);
                    }
                    else NEXT_SINK_GRID = SINK.nextGrid_maxQvalueMDP();
                }

                else if(Mode.equals("TSP")) {
                    NEXT_SINK_GRID = ROUND_GRID_LIST.get(index_grid);
                    if(index_grid == ROUND_GRID_LIST.size() - 1) { index_grid=-1; }

                }
                else if(Mode.equals("Greedy")) {

//                    if( index_grid < ROUND_GRID_LIST.size() ) {
//                        NEXT_SINK_GRID = ROUND_GRID_LIST.get(index_grid);
//                    }
//                    else NEXT_SINK_GRID = SINK.nextGrid_Greedy();
                    NEXT_SINK_GRID = SINK.nextGrid_Greedy();
                }
                else if(Mode.equals("Random")) {

                    NEXT_SINK_GRID = SINK.nextGrid_Random();
                }


                System.out.println("NEXT_SINK_GRID: " + NEXT_SINK_GRID.getGridName() + '\n');
            }


            eventPlotList.clear();
            for(int j = 0; j < GRID_LIST.size(); j++) {
                if(! GRID_LIST.get(j).getEventList().isEmpty()) {
                    eventPlotList.addAll(GRID_LIST.get(j).getEventList());
                }

            }

            repaint();

            File f1 = new File(Mode + "_VOI_1.txt");
            try {
                FileOutputStream writeOut = new FileOutputStream(f1,true);
                PrintWriter out = new PrintWriter(writeOut);
                out.print(SINK.getVOI() + " " + SINK.getNumEventsSensed() + " " + SINK.getNumEventsCollected() + "\n");
                out.close();
            } catch (IOException e) { e.printStackTrace(); }


            if(SINK.getNumEventsSensed() >= 100) {

                File f2 = new File(Mode + "_time_delay_1.txt");
                try {
                    FileOutputStream writeOut = new FileOutputStream(f2,true);
                    PrintWriter out = new PrintWriter(writeOut);

                    for(int k = 0; k < allEventsRecorder.size(); k++) {

                        int delay = allEventsRecorder.get(k).getCollectRound() - allEventsRecorder.get(k).getStartRound();
                        out.print(delay + "\n");
                    }

                    out.close();
                } catch (IOException e) { e.printStackTrace(); }

                t.cancel();
                t.purge();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File_Reader animals = new File_Reader();
        AnimalMonitoring animalMonitor = new AnimalMonitoring(animals.animalList);
    }

    double findDistanceBetweenTwoPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt((double) Math.pow((x2 - x1), 2)
                + Math.pow((y2 - y1), 2));
    }

    Grid findWhichGrid(double x1, double y1, List<Grid> GRID_LIST) {
        int dis = Integer.MAX_VALUE;
        int index = 0;
        for(int i = 0; i < GRID_LIST.size(); i++) {
            int d = (int)Math.sqrt(Math.pow((GRID_LIST.get(i).getX() - x1), 2)
                    + Math.pow((GRID_LIST.get(i).getY() - y1), 2));
            if(d < dis) {
                dis = d;
                index = i;
            }
        }

        return GRID_LIST.get(index);
    }

}
