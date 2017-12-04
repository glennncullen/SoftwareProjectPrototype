import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrototypeGraphics extends JPanel {

    private JButton northToSouth, eastToWest, bothRed;

    // road from left to right
    int[] trafficLightOne = {335, 250, 15, 15};
    int[] trafficLightTwo = {450, 335, 15, 15};
    // road from top to bottom
    int[] trafficLightThree = {435, 235, 15, 15};
    int[] trafficLightFour = {350, 350, 15, 15};

    public PrototypeGraphics(){
        northToSouth = new JButton("North to South");
        this.add(northToSouth);

        eastToWest = new JButton("East to West");
        this.add(eastToWest);

        bothRed = new JButton("Both Red");
        this.add(bothRed);

        northToSouth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Handler.changeLightsNorthToSouth();
            }
        });

        eastToWest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Handler.changeLightsEastToWest();
            }
        });

        bothRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Handler.changeLightsBothRed();
            }
        });

    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.GRAY);

        // Buildings
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, 350, 250);
        g.fillRect(450, 0, 350, 250);
        g.fillRect(0, 350, 350, 250);
        g.fillRect(450, 350, 350, 250);

        // Road Markings
        g.setColor(Color.WHITE);
        g.drawLine(0, 300, 350, 300);
        g.drawLine(350, 250, 350, 300);
        g.drawLine(450, 300, 800, 300);
        g.drawLine(450, 300, 450, 350);
        g.drawLine(400, 0, 400, 250);
        g.drawLine(400, 250, 450, 250);
        g.drawLine(400, 350, 400, 600);
        g.drawLine(350, 350, 400, 350);

        // traffic Lights
        if(Handler.trafficLights[0]) {
            g.setColor(Color.GREEN);
        }else{
            g.setColor(Color.RED);
        }
        g.fillRect(trafficLightOne[0], trafficLightOne[1], trafficLightOne[2], trafficLightOne[3]);
        g.fillRect(trafficLightTwo[0], trafficLightTwo[1], trafficLightTwo[2], trafficLightTwo[3]);

        if(Handler.trafficLights[1]) {
            g.setColor(Color.GREEN);
        }else{
            g.setColor(Color.RED);
        }
        g.fillRect(trafficLightThree[0], trafficLightThree[1], trafficLightThree[2], trafficLightThree[3]);
        g.fillRect(trafficLightFour[0], trafficLightFour[1], trafficLightFour[2], trafficLightFour[3]);

        // cars
        if(Handler.cars.size() > 0) {
            g.setColor(Color.CYAN);
            for (int i = 0; i < Handler.cars.size(); i++) {
                Car car = Handler.cars.get(i);
                int[] coor = car.getCoordinates();
                int[] dimensions = car.getDimensions();
                g.fillRect(coor[0], coor[1], dimensions[0], dimensions[1]);
            }
        }
    }

}
