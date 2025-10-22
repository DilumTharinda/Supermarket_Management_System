package SuperMarket_Management_System;

import javax.swing.*;
import java.awt.*;

public class Home extends JFrame {
    //constructor
    public Home(String username){
        setTitle(" SuperMarket Home");


        JLabel label = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.BLUE);

        add(label);

        //Window
        setSize(400, 200);
        setLocation(550, 350);
        getContentPane().setBackground(new Color(230, 255, 240));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
