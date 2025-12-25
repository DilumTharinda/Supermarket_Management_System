package SuperMarket_Management_System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Billing extends JFrame implements ActionListener {

    JTextField txtItemID, txtQuantity, txtProductName;
    JTextField txtCustomerID;
    JLabel lblGrandTotal,lblOrderIDDisplay;
    JTable table;
    DefaultTableModel model;
    JTextArea receiptArea;
    JButton btnAdd, btnPrint, btnClear, btnBack, btnSearch, btnSave;

    String username;
    String position;
    String currentStaffID;
    double grandTotal = 0.00;
    private String getStaffIdByUsername(String username) {
        String staffID = null;
        try (Connection conn = DataBase_Connection.getConnection()) {
            String sql = "SELECT Staff_ID FROM user WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                staffID = rs.getString("Staff_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffID;
    }
    private boolean isCustomerValid(String custID) {
        if (custID.isEmpty()) return true;
        try (Connection conn = DataBase_Connection.getConnection()) {
            String sql = "SELECT Customer_ID FROM customer WHERE Customer_ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, custID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Billing(String username, String position) {
        this.username = username;
        this.position = position;
        this.currentStaffID = getStaffIdByUsername(username);
        if (this.currentStaffID == null) {
            JOptionPane.showMessageDialog(this, "Error: Could not find Staff ID for user '" + username + "'.\nBilling may fail.");
        }

        setTitle("Billing Counter - " + position + ": " + username);

        setLayout(null);
        setSize(1200, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel heading = new JLabel("SUPERMARKET BILLING");
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        heading.setForeground(Color.BLUE);
        heading.setBounds(400, 10, 400, 40);
        add(heading);
        JLabel lblOrderIDTitle = new JLabel("Order ID:");
        lblOrderIDTitle.setBounds(20, 20, 100, 30);
        lblOrderIDTitle.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblOrderIDTitle);


        lblOrderIDDisplay = new JLabel();
        lblOrderIDDisplay.setBounds(100, 20, 150, 30);
        lblOrderIDDisplay.setFont(new Font("Arial", Font.BOLD, 14));
        lblOrderIDDisplay.setForeground(Color.RED);
        add(lblOrderIDDisplay);

        generateOrderID();
        JLabel lblCustID = new JLabel("Cust ID:");
        lblCustID.setBounds(140, 20, 80, 30); // Positioned to the right of Order ID
        lblCustID.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblCustID);

        txtCustomerID = new JTextField();
        txtCustomerID.setBounds(220, 20, 120, 30);
        txtCustomerID.setToolTipText("Leave empty for Walk-in Customer");
        add(txtCustomerID);

        JLabel lblItem = new JLabel("Item ID:");
        lblItem.setBounds(20, 80, 100, 30);
        add(lblItem);

        txtItemID = new JTextField();
        txtItemID.setBounds(100, 80, 150, 30);
        txtItemID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchItemName();
            }
        });
        add(txtItemID);

        // Search Button
        btnSearch = new JButton("Search");
        btnSearch.setBounds(260, 80, 80, 30);
        btnSearch.addActionListener(this);
        add(btnSearch);

        JLabel lblName = new JLabel("Item Name:");
        lblName.setBounds(20, 130, 100, 30);
        add(lblName);

        txtProductName = new JTextField();
        txtProductName.setBounds(100, 130, 150, 30);
        txtProductName.setEditable(false); // Read-only
        add(txtProductName);

        JLabel lblQty = new JLabel("Quantity:");
        lblQty.setBounds(20, 180, 100, 30);
        add(lblQty);

        txtQuantity = new JTextField();
        txtQuantity.setBounds(100, 180, 150, 30);
        txtQuantity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToBill();
            }
        });
        add(txtQuantity);

        btnAdd = new JButton("Add to Bill");
        btnAdd.setBounds(50, 230, 150, 40);
        btnAdd.setBackground(new Color(0, 153, 76)); // Green
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.addActionListener(this);
        add(btnAdd);

        btnClear = new JButton("Clear");
        btnClear.setBounds(220, 230, 100, 40);
        btnClear.setBackground(Color.ORANGE);
        btnClear.setFont(new Font("Arial", Font.BOLD, 14));
        btnClear.addActionListener(this);
        add(btnClear);

        // --- Bill Table (Center) ---
        String[] columnNames = {"ID", "Item", "Price", "Qty", "Total"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(380, 80, 500, 400);
        add(scrollPane);

        // --- Total & Print (Right Side) ---
        JLabel lblTotalText = new JLabel("Grand Total:");
        lblTotalText.setFont(new Font("Arial", Font.BOLD, 20));
        lblTotalText.setBounds(500, 500, 150, 30);
        add(lblTotalText);

        lblGrandTotal = new JLabel("Rs. 0.00");
        lblGrandTotal.setFont(new Font("Arial", Font.BOLD, 25));
        lblGrandTotal.setForeground(Color.RED);
        lblGrandTotal.setBounds(650, 500, 200, 30);
        add(lblGrandTotal);

        btnPrint = new JButton("Print & Save ");
        btnPrint.setBounds(900, 550, 150, 50);
        btnPrint.setBackground(Color.BLUE);
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setFont(new Font("Arial", Font.BOLD, 16));
        btnPrint.addActionListener(this);
        add(btnPrint);

        btnSave = new JButton("Save Only");
        btnSave.setBounds(740, 550, 150, 50); // Placed to the left of Print
        btnSave.setBackground(new Color(0, 102, 204)); // Darker Blue
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Arial", Font.BOLD, 16));
        btnSave.addActionListener(this);
        add(btnSave);

        // Receipt Preview Area
        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Better font for receipts
        JScrollPane scrollReceipt = new JScrollPane(receiptArea);
        scrollReceipt.setBounds(900, 80, 260, 400);
        add(scrollReceipt);

        // Back Button
        btnBack = new JButton("Back Home");
        btnBack.setBounds(20, 600, 120, 40);
        btnBack.setBackground(Color.DARK_GRAY);
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(this);
        add(btnBack);

        setVisible(true);
    }

    private void fetchItemName() {
        String id = txtItemID.getText().trim();
        if (id.isEmpty()) return;
        try (Connection conn = DataBase_Connection.getConnection()) {
            String sql = "SELECT IName FROM items WHERE Item_ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("IName"); // Updated Column
                txtProductName.setText(name);
                txtQuantity.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Item ID not found!");
                txtProductName.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }
    private int getQtyInCart(String itemID) {
        int currentQty = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            String tableID = model.getValueAt(i, 0).toString();
            if (tableID.equals(itemID)) {
                // Add up quantity if item appears multiple times
                currentQty += (int) model.getValueAt(i, 3);
            }
        }
        return currentQty;
    }


    private void addToBill() {
        String id = txtItemID.getText().trim();
        String qtyStr = txtQuantity.getText().trim();

        if (id.isEmpty() || qtyStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter ID and Quantity");
            return;
        }
        try (Connection conn = DataBase_Connection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT IName, Price, Quantity FROM items WHERE Item_ID = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("IName");
                double price = rs.getDouble("Price");
                int dbStock = rs.getInt("Quantity"); // Real stock in DB
                int cartQty = getQtyInCart(id);
                if (dbStock == 0) {
                    JOptionPane.showMessageDialog(this, "Stock is Empty (0)!");
                    return;
                }
                int newQty;
                try {
                    newQty = Integer.parseInt(qtyStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Quantity must be a number!");
                    return;
                }
                if ((cartQty + newQty) > dbStock) {
                    JOptionPane.showMessageDialog(this,
                            "Low Stock! Available: " + dbStock + "\nYou already have " + cartQty + " in the bill.");
                    return;
                }
                if (newQty <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantity must be greater than 0");
                    return;
                }
                double lineTotal = price * newQty;
                String formattedLineTotal = String.format("%.2f", lineTotal);
                model.addRow(new Object[]{id, name, price, newQty, Double.parseDouble(formattedLineTotal)});
                grandTotal += lineTotal;
                lblGrandTotal.setText(String.format("Rs. %.2f", grandTotal));
                txtItemID.setText("");
                txtProductName.setText("");
                txtQuantity.setText("");
                txtItemID.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Item ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }


    private void processBill(boolean shouldPrint) {
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Bill is empty!");
            return;
        }
        if (currentStaffID == null) {
            JOptionPane.showMessageDialog(this, "Cannot Save: Invalid Staff ID.");
            return;
        }
        String custIDInput = txtCustomerID.getText().trim();
        if (!custIDInput.isEmpty()) {
            if (!isCustomerValid(custIDInput)) {
                JOptionPane.showMessageDialog(this, "Invalid Customer ID! Please check or leave empty.");
                txtCustomerID.requestFocus();
                return;
            }
        }

        Connection conn = null;
        try {
            conn = DataBase_Connection.getConnection();
            conn.setAutoCommit(false);
            updateDatabaseStock(conn);
            String insertOrderSQL = "INSERT INTO customer_order (Order_ID, Staff_ID, Total_Price, Purchase_Date, Time,Customer_ID) VALUES (?, ?, ?, ?, ?,?)";
            PreparedStatement orderStmt = conn.prepareStatement(insertOrderSQL);
            LocalDateTime now = LocalDateTime.now();
            String currentDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String currentTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            if (custIDInput.isEmpty()) {
                orderStmt.setNull(6, java.sql.Types.VARCHAR); // Insert NULL for walk-ins
            } else {
                orderStmt.setString(6, custIDInput);
            }
            orderStmt.setInt(1, Integer.parseInt(lblOrderIDDisplay.getText())); // Order ID
            orderStmt.setString(2, currentStaffID);
            orderStmt.setDouble(3, grandTotal);
            orderStmt.setString(4, currentDate);
            orderStmt.setString(5, currentTime);
            orderStmt.executeUpdate();
            orderStmt.close();
            String insertItemSQL = "INSERT INTO Order_Items (Order_ID, Item_ID, OI_Quantity) VALUES (?, ?, ?)";
            PreparedStatement orderItemStmt = conn.prepareStatement(insertItemSQL);
            StringBuilder receiptText = new StringBuilder();
            receiptText.append("*************************************\n");
            receiptText.append("      SUPERMARKET RECEIPT      \n");
            receiptText.append("*************************************\n");
            receiptText.append("Order ID: " + lblOrderIDDisplay.getText() + "\n");
            if (!custIDInput.isEmpty()) {
                receiptText.append("Customer: " + custIDInput + "\n");
            }
            receiptText.append("Date:     " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n");
            receiptText.append("Cashier:  " + username + "\n");
            receiptText.append("-------------------------------------\n");
            receiptText.append(String.format("%-10s %-5s %-10s %-10s\n", "Item", "Qty", "Price", "Total"));
            receiptText.append("-------------------------------------\n");


            for (int i = 0; i < model.getRowCount(); i++) {
                String name = model.getValueAt(i, 1).toString();
                int qty = (int) model.getValueAt(i, 3);
                double price = (double) model.getValueAt(i, 2);
                double total = (double) model.getValueAt(i, 4);
                String itemID = model.getValueAt(i, 0).toString();
                orderItemStmt.setInt(1, Integer.parseInt(lblOrderIDDisplay.getText())); // Change variable name here
                orderItemStmt.setString(2, itemID);
                orderItemStmt.setInt(3, qty);
                orderItemStmt.addBatch();
                if (name.length() > 10) name = name.substring(0, 10);
                if (name.length() > 10) name = name.substring(0, 10);
                receiptText.append(String.format("%-10s %-5d %-10.2f %-10.2f\n", name, qty, price, total));
            }
            orderItemStmt.executeBatch();
            orderItemStmt.close();
            receiptText.append("-------------------------------------\n");
            receiptText.append(String.format("GRAND TOTAL:      Rs. %.2f\n", grandTotal));
            receiptText.append("*************************************\n");
            receiptText.append("    THANK YOU VISIT AGAIN!    \n");

            conn.commit();
            receiptArea.setText(receiptText.toString());

            boolean printSuccess = false;
            if (shouldPrint) {
                try {
                    printSuccess = receiptArea.print(null, null, false, null, null, true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Printing Error: " + e.getMessage());
                }
            }


            if (shouldPrint) {
                if (printSuccess) JOptionPane.showMessageDialog(this, "Bill Saved & Printed!");
                else JOptionPane.showMessageDialog(this, "Bill Saved (Printing Cancelled/Failed).");
            } else {
                JOptionPane.showMessageDialog(this, "Bill Saved Successfully!");
            }

                model.setRowCount(0);
                grandTotal = 0;
                lblGrandTotal.setText("Rs. 0.0");
                receiptArea.setText("");
                txtItemID.requestFocus();
                generateOrderID();

        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            JOptionPane.showMessageDialog(this, "Transaction Failed: " + e.getMessage());
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }

    }
    private void generateOrderID() {
        try (Connection conn = DataBase_Connection.getConnection()) {
            String sql = "SELECT Order_ID FROM customer_order ORDER BY Order_ID DESC LIMIT 1";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                int lastID = rs.getInt("Order_ID");
                int newID = lastID + 1;
                lblOrderIDDisplay.setText(String.valueOf(newID));
            } else {
                lblOrderIDDisplay.setText("001");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblOrderIDDisplay.setText("1001");
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            addToBill();
        } else if (e.getSource() == btnSearch) {
            fetchItemName();
        } else if (e.getSource() == btnSave) {
            processBill(false);
        }else if (e.getSource() == btnPrint) {
            processBill(true);
        } else if (e.getSource() == btnClear) {
            txtItemID.setText("");
            txtQuantity.setText("");
            txtProductName.setText("");
        } else if (e.getSource() == btnBack) {
            new Home(username, position);
            dispose();
        }
    }
    private void updateDatabaseStock(Connection conn) throws SQLException {
        String sql = "UPDATE items SET Quantity = Quantity - ? WHERE Item_ID = ?";
        PreparedStatement updateStock = conn.prepareStatement(sql);
        for (int i = 0; i < model.getRowCount(); i++) {
            String id = model.getValueAt(i, 0).toString();
            int qty = (int) model.getValueAt(i, 3);
            updateStock.setInt(1, qty);
            updateStock.setString(2, id);
            updateStock.addBatch();
        }
        updateStock.executeBatch();
        updateStock.close();
    }
}