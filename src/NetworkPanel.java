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
    List<Event> eventPlotList;
    int GLOBAL_TIME_ROUNDs;
    int counter = 0;

    NetworkPanel(SinkNode sink, List<Event> eventPlotList) {
        this.SINK = sink;
        this.eventPlotList = eventPlotList;
        this.GLOBAL_TIME_ROUNDs = SINK.flyTime;
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


        GLOBAL_TIME_ROUNDs = SINK.flyTime;
        for(int i = 0; i < eventPlotList.size(); i++) {

            if( (GLOBAL_TIME_ROUNDs - eventPlotList.get(i).getStartRound()) > Event.EVENT_VALID_TIME) {

                continue;
            }

            g2.drawImage(icon.getImage(), (int)eventPlotList.get(i).getX(), (int)eventPlotList.get(i).getY(),
                    40, 40, this);
            g2.drawString(eventPlotList.get(i).getAnimalID(), (int)eventPlotList.get(i).getX() + 10,
                    (int)eventPlotList.get(i).getY() + 20);
        }


        g2.drawImage(icon_UAV.getImage(), (int)SINK.getX(), (int)SINK.getY(), 60, 40, this);
        g2.setFont(font);
        g2.drawString("Simulation Time:"+ GLOBAL_TIME_ROUNDs, 1020, 50);
        g2.drawString("Current Mode: MDP", 1020, 100);
        g2.drawString("Total VOI_MDP: " + SINK.getVOI(), 1020, 150);
        g2.drawString("Total NumEventsSensed: " + SINK.getNumEventsSensed(), 1020, 200);
        g2.drawString("Total NumEventsCollected: " + SINK.getNumEventsCollected(), 1020, 250);
        g2.drawString("Average TimeDelay: " + SINK.getTimeDelay()/(float)SINK.getNumEventsCollected(), 1020, 300);

    }
}