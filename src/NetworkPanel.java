import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Created by jun on 6/6/17.
 */

class NetworkPanel extends JPanel {

    SinkNode sinknode;
    File_Reader animals;
    NetworkPanel(SinkNode sink, File_Reader animals) {
        this.sinknode = sink;
        this.animals = animals;
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
        ImageIcon icon_UAV = new ImageIcon("UAV.PNG");
        Font font = new Font("Monospaced", Font.BOLD, 18);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.MAGENTA);

        g2.drawImage(icon_UAV.getImage(), (int)sinknode.getX(), (int)sinknode.getY(), 120, 100, this);
        g2.setFont(font);
        g2.drawString("Current Mode: MDP", 1020, 100);
        g2.drawString("Total VOI_MDP: " + sinknode.getVOI(), 1020, 150);


        File writefile = new File("./result.txt");
        try {
            FileOutputStream writeOut = new FileOutputStream(writefile,true);
            PrintWriter out = new PrintWriter(writeOut);
            out.print(sinknode.getVOI() + "\r\n");
//            out.print(sinknode.getVOI() + " " + AverageDelay_MDP + "\r\n");
            out.close();
        } catch (IOException e) { e.printStackTrace(); }


    }
}
