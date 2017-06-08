import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;

class NetworkPanel extends JPanel {

    SinkNode SINK;
    List<Animal.AnimalPoint> animalPlotList;
    int global_rounds;

    NetworkPanel(SinkNode sink, List<Animal.AnimalPoint> animalPlotList, int global_rounds) {
        this.SINK = sink;
        this.animalPlotList = animalPlotList;
        this.global_rounds = global_rounds;
    }

    public void paintComponent(Graphics g) {
        g.drawLine(250, 0, 250, 1000);
        g.drawLine(500, 0, 500, 1000);
        g.drawLine(750, 0, 750, 1000);
        g.drawLine(1000, 0, 1000, 1000);
        g.drawLine(0, 250, 1000, 250);
        g.drawLine(0, 500, 1000, 500);
        g.drawLine(0, 750, 1000, 750);

        ImageIcon icon = new ImageIcon("zebra1.gif");
        ImageIcon icon_UAV = new ImageIcon("UAV.png");
        Font font = new Font("Monospaced", Font.BOLD, 18);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.MAGENTA);


        for(int i = 0; i < animalPlotList.size(); i++) {
            g2.drawImage(icon.getImage(), (int)animalPlotList.get(i).X, (int)animalPlotList.get(i).Y,
                    80, 80, this);
            g2.drawString(animalPlotList.get(i).animalID, (int)animalPlotList.get(i).X + 10,
                    (int)animalPlotList.get(i).Y + 20);
        }


        g2.drawImage(icon_UAV.getImage(), (int)SINK.getX(), (int)SINK.getY(), 80, 60, this);
        g2.setFont(font);
        g2.drawString("Time:"+ global_rounds, 1020, 50);
        g2.drawString("Current Mode: MDP", 1020, 100);
        g2.drawString("Total VOI_MDP: " + SINK.getVOI(), 1020, 150);


        File writefile = new File("./result.txt");
        try {
            FileOutputStream writeOut = new FileOutputStream(writefile,true);
            PrintWriter out = new PrintWriter(writeOut);
            out.print(SINK.getVOI() + "\r\n");
//            out.print(sinknode.getVOI() + " " + AverageDelay_MDP + "\r\n");
            out.close();
        } catch (IOException e) { e.printStackTrace(); }


    }
}