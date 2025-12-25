package SuperMarket_Management_System;

import javax.swing.*;
import java.awt.*;

public class Splash extends JFrame {
    public Splash() {
        ImageIcon splash = new ImageIcon(getClass().getClassLoader().getResource("Asset/SuperMarketIntro.gif"));
        JLabel background = new JLabel(splash);
        background.setBounds(0, 0, 1280, 720);// x: distance from left side, y: distance from top side
        add(background);
        setSize(1280, 720);
        setLocation(120, 80);
        setLayout(null);
        setResizable( false);
        setVisible(true);
        try {
            Thread.sleep(5000);
            setVisible(false);//hide window
            new Login();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String [] args){
      new Splash();
    }

}
