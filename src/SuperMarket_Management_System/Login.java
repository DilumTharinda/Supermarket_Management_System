package SuperMarket_Management_System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame implements ActionListener {

    JButton loginBtn = new JButton("Login"); // we can't write this in constructor
    JButton closeBtn = new JButton("Close");

    JTextField textUsername;  // Username = SuperMarket
    JPasswordField textPassword; // Password = SuperMarket

    String position; // will be passed to Home.java

    //Constructor
    public Login(){
        setTitle("Login");

        //Title
        JLabel login = new JLabel("Login System");
        login.setFont(new Font("Arial", Font.BOLD, 24));
        login.setForeground(Color.RED);
        login.setBounds(200,5,200,50);
        add(login);

        //Username - label
        JLabel username = new JLabel("UserName");
        username.setFont(new Font("Arial",Font.BOLD,18));
        username.setForeground(Color.black);
        username.setBounds(40,50,100,50);
        add(username);

        //Username - type
        textUsername = new JTextField();
        textUsername.setBounds(150,61,250,35);
        textUsername.setFont(new Font("Arial", Font.ITALIC, 18));
        //textUsername.setBackground(new Color(210, 255, 255));
        textUsername.setBorder(BorderFactory.createLineBorder(Color.RED, 4, true));
        add(textUsername);

        //Password - label
        JLabel password = new JLabel("Password");
        password.setFont(new Font("Arial",Font.BOLD,18));
        password.setForeground(Color.black);
        password.setBounds(40,100,100,50);
        add(password);

        //Password - type
        textPassword = new JPasswordField();
        textPassword.setBounds(150,105,250,35);
        textPassword.setFont(new Font("Arial", Font.ITALIC, 18));
        //textPassword.setBackground(new Color(210, 255, 255));
        textPassword.setBorder(BorderFactory.createLineBorder(Color.RED, 4, true));
        add(textPassword);

        //Login Button
        loginBtn.setBounds(210,160,100,40);
        loginBtn.setFont(new Font("Arial",Font.BOLD,16));
        loginBtn.setBackground(Color.black);
        loginBtn.setForeground(Color.white);
        loginBtn.addActionListener(this);
        add(loginBtn);

        //Lock Icon
        ImageIcon locked = new ImageIcon(ClassLoader.getSystemResource("asset/Locked.png"));
        Image lock_Size = locked.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT);
        JLabel label = new JLabel(new ImageIcon(lock_Size));
        label.setBounds(420,58,80,80);
        add(label);

        //Back Button
        closeBtn.setBounds(210,210,100,40);
        closeBtn.setFont(new Font("Arial",Font.BOLD,16));
        closeBtn.setBackground(Color.black);
        closeBtn.setForeground(Color.white);
        closeBtn.addActionListener(this);
        add(closeBtn);

        //Window settings
        setSize(550,300);
        setLocation(480,300);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(255, 255, 255));
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == loginBtn) {

            String username = textUsername.getText();
            String password = new String(textPassword.getPassword());

            if (username.isEmpty() || password.isEmpty()){
                JOptionPane.showMessageDialog(this,"Please enter both UserName and Password");
                return;
            }

            try{
                //connect database
                Connection connection = DataBase_Connection.getConnection();

                String sql_query = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement pre_stmt = connection.prepareStatement(sql_query);
                pre_stmt.setString(1, username);
                pre_stmt.setString(2, password);

                ResultSet rs = pre_stmt.executeQuery();

                if (rs.next()) { // check if there is a matching record
                    String dbUsername = rs.getString("username");
                    String dbPassword = rs.getString("password");
                    position = rs.getString("Position");

                    if (username.equals(dbUsername) && password.equals(dbPassword)) {
                        //JOptionPane.showMessageDialog(this,"Login Successful");
                        this.dispose(); // close login window
                        new Home(username, position);
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                }

                rs.close();
                pre_stmt.close();
                connection.close();

            } catch (SQLException s){
                s.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error: " + s.getMessage());
            }

        } else if(event.getSource() == closeBtn){
            this.dispose();
        }
    }

    public static void main(String[] args){
        new Login();
    }

}