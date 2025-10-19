package SuperMarket_Management_System;

import javax.swing.*;
import java.awt.*;

public class Splash extends JFrame {

    //Constructor
    public Splash() {

        //Background image
        //Load image from Asset
        ImageIcon splash = new ImageIcon(ClassLoader.getSystemResource("asset/splash.png"));
        // Resize the image
        Image scaledImage = splash.getImage().getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
        // Create label and set image
        JLabel background = new JLabel(new ImageIcon(scaledImage));
        // Set position and add to frame
        background.setBounds(0, 0, 1280, 720);// x: distance from left side, y: distance from top side
        add(background);


        //Window
        setSize(1280, 720);
        //default window open location
        setLocation(120, 80);// x : from left side on pc display, y : from top side on pc display
        setLayout(null);//manually set positions and sizes of components using setBounds().
        setResizable( false);// lock window resize
        setVisible(true);



        //Close window after 5 sec

        try {
            /*
            * Thread - mini program that runs inside main program
            * sleep - close window after 5 sec
             */
            Thread.sleep(5000);
            setVisible(false);//hide window
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String [] args){
      new Splash();
    }

}
