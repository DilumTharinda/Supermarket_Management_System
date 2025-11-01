package SuperMarket_Management_System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame implements ActionListener {

    //Tables Buttons
    JButton Customer;
    JButton CustomerOrder;
    JButton Items;
    JButton Staff;
    JButton Supplier;
    JButton SupplierOrder;
    JButton Users;
    JButton LogOut;

    // Panel to hold buttons
    JPanel buttonPanel1;
    JPanel buttonPanel2;

    String username;
    String position;

    //constructor
    public Home(String username,String position){
        this.username = username;
        this.position = position;

        setTitle(" SuperMarket Home");

        //Back Image
        //Load image from asset
        ImageIcon home = new ImageIcon(ClassLoader.getSystemResource("asset/Home.jpg"));
        //set size
        Image scaledImage = home.getImage().getScaledInstance(1280,720,Image.SCALE_SMOOTH);
        //Create Label
        JLabel background = new JLabel(new ImageIcon(scaledImage));
        // Set position and add to frame
        background.setBounds(0, 0, 1280, 720);
        add(background);

        //User
        JLabel label = new JLabel("Welcome, " + username + "!       Position : "+ position);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBounds(20,630,700,50);
        label.setForeground(Color.BLUE);
        background.add(label);//Add text on background image

        //Heading
        JLabel heading = new JLabel("SuperMarket Management System");
        heading.setBounds(300,20,700,50);
        heading.setFont(new Font("Raleway",Font.BOLD,40));
        background.add(heading);

        // Create panel for buttons with auto-alignment
        createButtonPanel1(background);
        createButtonPanel2(background);
        //  Method - buttons for each table
        createButtons();

        // Method - visibility based on position
        setButtonVisibility();


        //Window
        setSize(1280, 720);
        setLocation(120, 80);
        getContentPane().setBackground(new Color(230, 255, 240));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void createButtonPanel1(JLabel background){
        buttonPanel1 = new JPanel();
        buttonPanel1.setBounds(20,120,200,450);
        buttonPanel1.setOpaque(false);// Make panel transparent to show background
        buttonPanel1.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));// Center alignment with gaps
        background.add(buttonPanel1);
    }

    private void createButtonPanel2(JLabel background){
        buttonPanel2 = new JPanel();
        buttonPanel2.setBounds(1045,120,200,450);
        buttonPanel2.setOpaque(false);// Make panel transparent to show background
        buttonPanel2.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));// Center alignment with gaps
        background.add(buttonPanel2);
    }

    private void createButtons(){
        //Customer
        Customer = new JButton("Customer");
       //instead setbound
        Customer.setPreferredSize(new Dimension(180, 70));//This tells the layout manager that this button 220 pixels wide and 70 pixels tall.
        Customer.setFont(new Font("Arial", Font.BOLD,20));
        Customer.setBackground(new Color(70,130,180));
        Customer.setForeground(Color.WHITE);
        Customer.setFocusable(false);//Don’t allow this component to gain keyboard focus
       Customer.addActionListener(this);
       buttonPanel1.add(Customer);

        //Staff
        Staff = new JButton("Staff");
        Staff.setPreferredSize(new Dimension(180, 70));
        Staff.setFont(new Font("Arial", Font.BOLD,20));
        Staff.setBackground(new Color(70,130,180));
        Staff.setForeground(Color.WHITE);
        Staff.setFocusable(false);
        Staff.addActionListener(this);
        buttonPanel1.add(Staff);

        //Items
        Items = new JButton("Items");
        Items.setPreferredSize(new Dimension(180, 70));
        Items.setFont(new Font("Arial", Font.BOLD,20));
        Items.setBackground(new Color(70,130,180));
        Items.setForeground(Color.WHITE);
        Items.setFocusable(false);
        Items.addActionListener(this);
        buttonPanel1.add(Items);

        //Supplier
        Supplier = new JButton("Supplier");
        Supplier.setPreferredSize(new Dimension(180, 70));
        Supplier.setFont(new Font("Arial", Font.BOLD,20));
        Supplier.setBackground(new Color(70,130,180));
        Supplier.setForeground(Color.WHITE);
        Supplier.setFocusable(false);
        Supplier.addActionListener(this);
        buttonPanel1.add(Supplier);

        //CustomOrder
        CustomerOrder = new JButton("Custom Order");
        CustomerOrder.setPreferredSize(new Dimension(180, 70));
        CustomerOrder.setFont(new Font("Arial", Font.BOLD,20));
        CustomerOrder.setBackground(new Color(70,130,180));
        CustomerOrder.setForeground(Color.WHITE);
        CustomerOrder.setFocusable(false);
        CustomerOrder.addActionListener(this);
        buttonPanel2.add(CustomerOrder);

        //SupplierOrder
        SupplierOrder = new JButton("Supplier Order");
        SupplierOrder.setPreferredSize(new Dimension(180, 70));
        SupplierOrder.setFont(new Font("Arial", Font.BOLD,20));
        SupplierOrder.setBackground(new Color(70,130,180));
        SupplierOrder.setForeground(Color.WHITE);
        SupplierOrder.setFocusable(false);
        SupplierOrder.addActionListener(this);
        buttonPanel2.add(SupplierOrder);

        //Users
        Users = new JButton("Users");
        Users.setPreferredSize(new Dimension(180, 70));
        Users.setFont(new Font("Arial", Font.BOLD,20));
        Users.setBackground(new Color(70,130,180));
        Users.setForeground(Color.WHITE);
        Users.setFocusable(false);
        Users.addActionListener(this);
        buttonPanel2.add(Users);

        //LogOut
        LogOut = new JButton("LogOut");
        LogOut.setPreferredSize(new Dimension(180, 70));
        LogOut.setFont(new Font("Arial", Font.BOLD,20));
        LogOut.setBackground(new Color(70,130,180));
        LogOut.setForeground(Color.WHITE);
        LogOut.setFocusable(false);
        LogOut.addActionListener(this);
        buttonPanel2.add(LogOut);

    }

    private void setButtonVisibility(){
        Customer.setVisible(false);
        CustomerOrder.setVisible(false);
        Items.setVisible(false);
        Staff.setVisible(false);
        Supplier.setVisible(false);
        SupplierOrder.setVisible(false);
        Users.setVisible(false);
        LogOut.setVisible(true);

        switch (position.toLowerCase()){
            case "manager":
            case "admin":
                Customer.setVisible(true);
                CustomerOrder.setVisible(true);
                Items.setVisible(true);
                Staff.setVisible(true);
                Supplier.setVisible(true);
                SupplierOrder.setVisible(true);
                Users.setVisible(true);
                break;

            case "supervisor":
                Customer.setVisible(true);
                CustomerOrder.setVisible(true);
                Items.setVisible(true);
                Staff.setVisible(true);
                Supplier.setVisible(true);
                SupplierOrder.setVisible(true);
                //remove users access
                break;

            case "cashier":
                Customer.setVisible(true);
                CustomerOrder.setVisible(true);
                Items.setVisible(true);
                //remove users, staff, supplier, supplier order access
                break;

            case "storekeeper":
                Items.setVisible(true);
                Supplier.setVisible(true);
                SupplierOrder.setVisible(true);
                //remove users,staff, customer and customer order access
                break;

            case "hr":
                // HR has access to: staff, users, customer_order, supplier_order
                Staff.setVisible(true);
                Users.setVisible(true);
                CustomerOrder.setVisible(true);
                SupplierOrder.setVisible(true);
                break;

            case "it":
                Users.setVisible(true);
                Staff.setVisible(true);
                Customer.setVisible(true);
                break;
            case "supplier":
                // Supplier has access to: supplier_order, supplier
                SupplierOrder.setVisible(true);
                Supplier.setVisible(true);
                break;

            default:
                JOptionPane.showMessageDialog(this,
                        "Position '" + position + "' is not recognized. Please contact administrator.",
                        "Access Error",
                        JOptionPane.WARNING_MESSAGE);
                break;

        }
        // Refresh the panel  after visibility changes
        buttonPanel1.revalidate();
        buttonPanel1.repaint();

        buttonPanel2.revalidate();
        buttonPanel2.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == Customer) {
           this.dispose();
           new Customer(username,position);

        } else if (event.getSource() == CustomerOrder) {
            this.dispose();
           new Customer_Order(username,position);
        } else if (event.getSource() == Items) {
            this.dispose();
           new Items(username,position);

        } else if (event.getSource() == Staff) {
            this.dispose();
           new Staff(username,position);

        } else if (event.getSource() == Supplier) {
            this.dispose();
          new Supplier(username,position);

        } else if (event.getSource() == SupplierOrder) {
            this.dispose();
           new SupplierOrder(username,position);

        } else if (event.getSource() == Users) {
            this.dispose();
            new Users(username,position);
        } else if (event.getSource() == LogOut) {
            this.dispose();
            new Login();
        }
    }


}
