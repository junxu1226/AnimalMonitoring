/**
 * Created by jun on 6/6/17.
 */
import java.io.IOException;
import javax.swing.JFrame;
import java.awt.*;
import java.util.*;
import java.util.List;


public class AnimalMonitoring extends JFrame {


    int global_rounds;  // silumation time in rounds

    Grid [][] allGrids;
    List<Grid> gridList;
    int index_grid = 0;
    Grid next_SINK_GRID = gridList.get(index_grid); // set the first Grid "A1" as the next grid


    String Mode = "MDP";
    SinkNode SINK = new SinkNode();


    List<Animal> animalList;


    public AnimalMonitoring() {

        int num_grids_each_line = 4;
        InitialGrids grids = new InitialGrids(num_grids_each_line);
        this.allGrids = grids.getMap();
        this.gridList = grids.getGridList();


        File_Reader animals = new File_Reader();
        this.animalList = animals.animalList;


        NetworkPanel NMPanel = new NetworkPanel(SINK, animals);
        this.add(NMPanel);
        this.setBackground(Color.LIGHT_GRAY);
        this.createVisualizer(1500, 1000);
        this.setTitle("Max Value of Information on Animal Monitoring");






        Timer t = new Timer();
        Task task = new Task(this);
        t.schedule(task, 0, 50);
    }



    private void createVisualizer(int width, int height) {
        this.setSize(width, height);
        this.setVisible(true);
    }

    class Task extends TimerTask {
        AnimalMonitoring animalMonitor;
        Date theBeginTime = new Date(113, 01, 01, 00, 00, 00);
        long startTime = theBeginTime.getTime();
        long newTimeInMilliSeconds = startTime;
        double samplingTimeOfSimulation = 1800.0; // in seconds

        int index = 0;
        public Task(AnimalMonitoring model) {
            super();
            this.animalMonitor = model;
        }
        public void run() {
            global_rounds = index++;
            newTimeInMilliSeconds = startTime + (long) samplingTimeOfSimulation
                    * 1000 * global_rounds;
            Date newDate = new Date(newTimeInMilliSeconds);

            System.out.print("newDate is " + newDate);

            int i = 0;
            while(i < animalList.size()) {
                Animal.AnimalPoint point = animalList.get(i).getPoint();

                if(newDate.equals(point.date)) {
                    Grid grid = findWhichGrid(point.X, point.Y, gridList);
                    grid.addEvent(animalList.get(i).getAnimalID(), global_rounds);
                }
                i++;
            }


            double tmpDis_SINK = findDistanceBetweenTwoPoints(SINK.getX(), SINK.getY(),
                    next_SINK_GRID.getX(), next_SINK_GRID.getY());

            if(tmpDis_SINK > SINK.getSpeed()) {
                double Direc_X = (next_SINK_GRID.getX() - SINK.getX())/tmpDis_SINK;
                double Direc_Y = (next_SINK_GRID.getY() - SINK.getY())/tmpDis_SINK;
                SINK.setX(SINK.getSpeed()*Direc_X);
                SINK.setY(SINK.getSpeed()* Direc_Y);
            }
            else { // UAV gets the collection point, update everything, VOI, next Grid
                // next_SINK_GRID is current Grid
                index_grid++;

                double[] events_info = next_SINK_GRID.collectEvents(global_rounds);
                // events_info shape: double[] = [init_rewards, real_rewards,
                // num_total_events, num_collected_events, timeDelay]

                SINK.update(next_SINK_GRID.getX(), next_SINK_GRID.getY(),
                        events_info[1], next_SINK_GRID);

                SINK.updateQvalues_onPath(events_info[0], 0.5, 0.8);

                if(index_grid < gridList.size() && Mode.equals("MDP")) {
                    next_SINK_GRID = gridList.get(index_grid);
                }
                else next_SINK_GRID = SINK.nextGrid_probability();
            }
            repaint();
        }
    }

    public static void main(String[] args) throws IOException {
        AnimalMonitoring animalMonitor = new AnimalMonitoring();
    }

    double findDistanceBetweenTwoPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt((double) Math.pow((x2 - x1), 2)
                + Math.pow((y2 - y1), 2));
    }

    Grid findWhichGrid(double x1, double y1, List<Grid> gridList) {
        int dis = Integer.MAX_VALUE;
        int index = 0;
        for(int i = 0; i < gridList.size(); i++) {
            int d = (int)Math.sqrt(Math.pow((gridList.get(i).getX() - x1), 2)
                    + Math.pow((gridList.get(i).getY() - y1), 2));
            if(d < dis) {
                dis = d;
                index = i;
            }
        }

        return gridList.get(index);
    }

}
