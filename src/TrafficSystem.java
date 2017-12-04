import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

import static java.awt.Frame.HAND_CURSOR;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class TrafficSystem {

    public static void main(String[] args) {
        Handler.init();
        NNHandler.init();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE );
        final PrototypeGraphics graphics = new PrototypeGraphics();
        Dimension dimension = new Dimension(800, 600);
        frame.add(graphics);
        frame.setSize(dimension);
        frame.setVisible(true);

        ActionListener simulation = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(Handler.timer.getDelay());
                Handler.addCar((int) Math.round(Math.random() * 14));
                graphics.repaint();
            }
        };


        ActionListener carNetwork = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Handler.cars.size() > 0){
                    Handler.updateCars();
                    NNHandler.calculateInput();
                    NNHandler.calculateHidden();
                    NNHandler.activation();
                    NNHandler.calculateOutput();
                    graphics.repaint();
                }
            }
        };


        Handler.simulationTimer = new Timer(Handler.SimulationTimerSpeed, simulation);
        Handler.simulationTimer.setDelay(Handler.SimulationTimerSpeed);
        Handler.simulationTimer.start();

        Handler.carTimer = new Timer(Handler.carTimerSpeed, carNetwork);
        Handler.carTimer.setDelay(Handler.carTimerSpeed);
        Handler.carTimer.start();



    }

}
