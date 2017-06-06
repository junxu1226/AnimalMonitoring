/**
 * Created by jun on 6/6/17.
 */
import java.io.IOException;
import javax.swing.JFrame;
import java.awt.*;

public class AnimalMonitoring extends JFrame {



    Grid [][] allGrids;

    SinkNode MDP = new SinkNode();
    double MDP_X = MDP.getX();
    double MDP_Y = MDP.getY();
    double MDP_VOI = MDP.getVOI();
    Queue<Grid> MDP_path = MDP.getPath();
    Grid MDP_Grid = MDP.getCurrentGrid();
    Grid next_MDP_Grid;


    public AnimalMonitoring() {

        int num_grids_each_line = 4;
        NetworkPanel NMPanel = new NetworkPanel();

        InitialGrids grids = new InitialGrids(num_grids_each_line);
        this.allGrids = grids.getMap();
        this.add(NMPanel);
        this.setBackground(Color.LIGHT_GRAY);
        this.createVisualizer(1500, 1000);
        this.setTitle("Max Value of Information on Animal Monitoring");

        next_MDP_Grid = MDP.nextGrid_probabilityt();

    }

    private void createVisualizer(int width, int height) {
        this.setSize(width, height);
        this.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        AnimalMonitoring animalMonitor = new AnimalMonitoring();
    }

}
