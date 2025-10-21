package SuperMarket_Management_System;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {


    //Constructor
    public Login(){

        //Title
        JLabel login = new JLabel("Login System");
        login.setFont(new Font("Arial", Font.BOLD, 24));//FontName, FontStyle, FontSize
        login.setForeground(Color.RED);//change color
        login.setBounds(200,5,200,50);
        add(login);

        //Username - text
        JLabel username = new JLabel("UserName");
        username.setFont(new Font("Arial",Font.BOLD,18));
        username.setForeground(Color.black);
        username.setBounds(40,50,100,50);
        add(username);

        //Username - type
        JTextField textUsername = new JTextField();
        textUsername.setBounds(150,61,250,35);
        textUsername.setFont(new Font("Arial", Font.ITALIC, 18)); // Font name, style, size
        textUsername.setBackground(new Color(210, 255, 255));
        textUsername.setBorder(BorderFactory.createLineBorder(Color.RED, 4, true));
        add(textUsername);

        //Password - text
        JLabel password = new JLabel("Password");
        password.setFont(new Font("Arial",Font.BOLD,18));
        password.setForeground(Color.black);
        password.setBounds(40,100,100,50);
        add(password);

        //Passward - type
        JPasswordField textPassword = new JPasswordField();
        textPassword.setBounds(150,105,250,35);
        textPassword.setFont(new Font("Arial", Font.ITALIC, 18));
        textPassword.setBackground(new Color(210, 255, 255));
        textPassword.setBorder(BorderFactory.createLineBorder(Color.RED, 4, true));
        add(textPassword);

        //Login Button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(210,160,100,40);
        loginBtn.setFont(new Font("Arial",Font.BOLD,16));
        loginBtn.setBackground(Color.black);
        loginBtn.setForeground(Color.white);//when clicked
        add(loginBtn);

        //Lock Icon
        ImageIcon locked = new ImageIcon(ClassLoader.getSystemResource("asset/Locked.png"));
        Image lock_Size = locked.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
        JLabel label = new JLabel(new ImageIcon(lock_Size));
        label.setBounds(400,60,100,100);
        add(label);

        //Back Button
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(210,210,100,40);
        backBtn.setFont(new Font("Arial",Font.BOLD,16));
        backBtn.setBackground(Color.black);
        backBtn.setForeground(Color.white);//when clicked
        add(backBtn);

        //Window
        setSize(550,300);
        setLocation(480,300);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(252, 222, 205)); // set background color
        setVisible(true);
    }

    public static
    void main(String [] args){
        new Login();
    }
}
