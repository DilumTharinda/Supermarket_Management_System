package SuperMarket_Management_System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;

public class Customer_Order extends JFrame implements ActionListener {

    // Buttons
    JButton btnAdd, btnUpdate, btnDelete, btnPrint, btnBack, btnRefresh;

    // Input fields
    JTextField txtOrderID, txtStaffID, txtItemID, txtQuantity, txtCustomerID, txtPurchaseDate, txtPaymentMethod, txtTotalPrice;

    // Table
    JTable table;
    DefaultTableModel model;

    // User info for permissions
    String username;
    String position;

    // Permission flags
    boolean canAdd = false;
    boolean canUpdate = false;
    boolean canDelete = false;

    //Constructor
    public Customer_Order(String username, String position) {
        this.username = username;
        this.position = position;

        setTitle("Customer Order Management - " + username + " (" + position + ")");

        // Set permissions based on position
        setPermissions();

        // Heading
        JLabel heading = new JLabel("Customer Order Management");
        heading.setBounds(380, 10, 440, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 30));
        heading.setForeground(new Color(70, 130, 180));
        add(heading);

        // Input Panel
        createInputPanel();

        // Button Panel
        createButtonPanel();

        // Table Panel
        createTablePanel();

        // Load data from database
        loadTableData();

        // Window
        setSize(1200, 700);
        setLocation(150, 80);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    // Set permissions based on user position
    private void setPermissions() {
        switch (position.toLowerCase()) {
            case "manager":
            case "admin":
            case "supervisor":
                canAdd = true;
                canUpdate = true;
                canDelete = true;
                break;

            case "cashier":
                canAdd = true;
                canUpdate = true;
                canDelete = false; // Cashiers cannot delete
                break;

            default:
                canAdd = false;
                canUpdate = false;
                canDelete = false;
                break;
        }
    }

    // Create input panel
    private void createInputPanel() {
        // OrderID
        JLabel lblOrderID = new JLabel("Order ID:");
        lblOrderID.setBounds(50, 70, 120, 25);
        lblOrderID.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblOrderID);

        txtOrderID = new JTextField();
        txtOrderID.setBounds(170, 70, 150, 25);
        add(txtOrderID);

        // Staff ID
        JLabel lblStaffID= new JLabel("Staff ID:");
        lblStaffID.setBounds(350, 70, 120, 25);
        lblStaffID.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblStaffID);

        txtStaffID = new JTextField();
        txtStaffID.setBounds(470, 70, 150, 25);
        add(txtStaffID);

        // Item ID
        JLabel lblItemID = new JLabel("Item ID:");
        lblItemID.setBounds(650, 70, 120, 25);
        lblItemID.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblItemID);

        txtItemID = new JTextField();
        txtItemID.setBounds(770, 70, 150, 25);
        add(txtItemID);

        // Quantity
        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setBounds(950, 70, 120, 25);
        lblQuantity.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblQuantity);

        txtQuantity = new JTextField();
        txtQuantity.setBounds(1050, 70, 100, 25);
        add(txtQuantity);

        // Customer ID
        JLabel lblCustomerID = new JLabel("Customer ID:");
        lblCustomerID.setBounds(50, 110, 120, 25);
        lblCustomerID.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblCustomerID);

        txtCustomerID = new JTextField();
        txtCustomerID.setBounds(170, 110, 150, 25);
        add(txtCustomerID);

        // Purchase Date
        JLabel lblPurchaseDate = new JLabel("Purchase Date:");
        lblPurchaseDate.setBounds(350, 110, 120, 25);
        lblPurchaseDate.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblPurchaseDate);

        txtPurchaseDate = new JTextField();
        txtPurchaseDate.setBounds(470, 110, 150, 25);
        add(txtPurchaseDate);

        // Payment Method
        JLabel lblPaymentMethod= new JLabel("Payment Method:");
        lblPaymentMethod.setBounds(650, 110, 120, 25);
        lblPaymentMethod.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblPaymentMethod);

        txtPaymentMethod = new JTextField();
        txtPaymentMethod.setBounds(770, 110, 150, 25);
        add(txtPaymentMethod);

        // Total Price
        JLabel lblTotalPrice = new JLabel("Total Price:");
        lblTotalPrice.setBounds(950, 110, 120, 25);
        lblTotalPrice.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTotalPrice);

        txtTotalPrice = new JTextField();
        txtTotalPrice.setBounds(1050, 110, 100, 25);
        add(txtTotalPrice);
    }

    // Create button panel
    private void createButtonPanel() {
        // Add Button
        btnAdd = new JButton("Add");
        btnAdd.setBounds(50, 160, 100, 35);
        btnAdd.setBackground(new Color(34, 139, 34));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.addActionListener(this);
        btnAdd.setEnabled(canAdd);
        add(btnAdd);

        // Update Button
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(170, 160, 100, 35);
        btnUpdate.setBackground(new Color(255, 140, 0));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFont(new Font("Arial", Font.BOLD, 14));
        btnUpdate.addActionListener(this);
        btnUpdate.setEnabled(canUpdate);
        add(btnUpdate);

        // Delete Button
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(290, 160, 100, 35);
        btnDelete.setBackground(new Color(220, 20, 60));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Arial", Font.BOLD, 14));
        btnDelete.addActionListener(this);
        btnDelete.setEnabled(canDelete);
        add(btnDelete);

        // Refresh Button
        btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(410, 160, 100, 35);
        btnRefresh.setBackground(new Color(70, 130, 180));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 14));
        btnRefresh.addActionListener(this);
        add(btnRefresh);

        // Print Button
        btnPrint = new JButton("Print Excel");
        btnPrint.setBounds(530, 160, 120, 35);
        btnPrint.setBackground(new Color(128, 0, 128));
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setFont(new Font("Arial", Font.BOLD, 14));
        btnPrint.addActionListener(this);
        add(btnPrint);

        // Back Button
        btnBack = new JButton("Back");
        btnBack.setBounds(1020, 160, 100, 35);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.addActionListener(this);
        add(btnBack);
    }

    // Create table panel
    private void createTablePanel() {
        // Table model
        String[] columnNames = {"Order ID", "Staff ID", "Item ID", "Quantity", "Customer ID", "Purchase Date", "Payment Method", "Total Price"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);

        // Add selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtOrderID.setText(model.getValueAt(row, 0).toString());
                txtStaffID.setText(model.getValueAt(row, 1).toString());
                txtItemID.setText(model.getValueAt(row, 2).toString());
                txtQuantity.setText(model.getValueAt(row, 3).toString());
                txtCustomerID.setText(model.getValueAt(row, 4).toString());
                txtPurchaseDate.setText(model.getValueAt(row, 5).toString());
                txtPaymentMethod.setText(model.getValueAt(row, 6).toString());
                txtTotalPrice.setText(model.getValueAt(row, 7).toString());
            }
        });

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 220, 1100, 400);
        add(scrollPane);
    }

    // Load data from database
    private void loadTableData() {
        try {
            Connection connection = DataBase_Connection.getConnection();
            String query = "SELECT * FROM customer_order";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Clear existing data
            model.setRowCount(0);

            // Add rows to table
            while (rs.next()) {
                String OrderID = rs.getString("Order_ID");
                String StaffID = rs.getString("Staff_ID");
                String ItemID = rs.getString("Item_ID");
                String Quantity = rs.getString("Quantity");
                String CustomerID = rs.getString("Customer_ID");
                String PurchaseDate = rs.getString("Purchase_Date");
                String PaymentMethod = rs.getString("Payment_method");
                String TotalPrice = rs.getString("Total_Price");

                model.addRow(new Object[]{OrderID, StaffID, ItemID, Quantity, CustomerID,PurchaseDate,PaymentMethod,TotalPrice});
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    // Add new customer order
    private void addCustomerorder() {
        String OrderID= txtOrderID.getText().trim();
        String StaffID = txtStaffID.getText().trim();
        String ItemID = txtItemID.getText().trim();
        String Quantity = txtQuantity.getText().trim();
        String CustomerID = txtCustomerID.getText().trim();
        String PurchaseDate = txtPurchaseDate.getText().trim();
        String PaymentMethod = txtPaymentMethod.getText().trim();
        String TotalPrice = txtTotalPrice.getText().trim();

        if (OrderID.isEmpty() || ItemID.isEmpty() || CustomerID.isEmpty() || TotalPrice.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!");
            return;
        }

        try {
            Connection connection = DataBase_Connection.getConnection();
            String query = "INSERT INTO customer_order (Order_ID, Staff_ID , Item_ID, Quantity, Customer_ID, Purchase_Date, Payment_method, Total_Price) VALUES (?, ?, ?, ?, ? ,? ,? ,?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, OrderID);
            pstmt.setString(2, StaffID);
            pstmt.setString(3, ItemID);
            pstmt.setString(4, Quantity);
            pstmt.setString(5, CustomerID);
            pstmt.setString(6, PurchaseDate);
            pstmt.setString(7, PaymentMethod);
            pstmt.setString(8, TotalPrice);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Customer Order added successfully!");
                loadTableData();
                clearFields();
            }

            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding customer order: " + e.getMessage());
        }
    }

    // Update customer order
    private void updateCustomerorder() {
        String OrderID= txtOrderID.getText().trim();
        String StaffID = txtStaffID.getText().trim();
        String ItemID = txtItemID.getText().trim();
        String Quantity = txtQuantity.getText().trim();
        String CustomerID = txtCustomerID.getText().trim();
        String PurchaseDate = txtPurchaseDate.getText().trim();
        String PaymentMethod = txtPaymentMethod.getText().trim();
        String TotalPrice = txtTotalPrice.getText().trim();

        if (OrderID.isEmpty() || ItemID.isEmpty() || CustomerID.isEmpty() || TotalPrice.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!");
            return;
        }

        try {
            Connection connection = DataBase_Connection.getConnection();
            String query = "UPDATE customer_order SET Staff_ID=?, Item_ID=?, Quantity=?, Customer_ID=?, Purchase_Date=?, Payment_method=?, Total_Price=? WHERE Order_ID=?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, StaffID);
            pstmt.setString(2, ItemID);
            pstmt.setString(3, Quantity);
            pstmt.setString(4, CustomerID);
            pstmt.setString(5, PurchaseDate);
            pstmt.setString(6, PaymentMethod);
            pstmt.setString(7, TotalPrice);
            pstmt.setString(8, OrderID);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Customer order updated successfully!");
                loadTableData();
                clearFields();
            }

            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating customer order: " + e.getMessage());
        }
    }

    // Delete customer order
    private void deleteCustomer() {
        String OrderID = txtOrderID.getText().trim();

        if (OrderID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a customer order to delete!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this customer order?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection connection = DataBase_Connection.getConnection();
                String query = "DELETE FROM customer_order WHERE Order_ID=?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, OrderID);

                int result = pstmt.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Customer order deleted successfully!");
                    loadTableData();
                    clearFields();
                }

                pstmt.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting customer order: " + e.getMessage());
            }
        }
    }

    // Print to Excel
    private void printToExcel() {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("CustomersOrder");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < table.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(table.getColumnName(i));
            }

            // Create data rows
            for (int i = 0; i < table.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < table.getColumnCount(); j++) {
                    Cell cell = row.createCell(j);
                    Object value = table.getValueAt(i, j);
                    cell.setCellValue(value != null ? value.toString() : "");
                }
            }

            // Auto-size columns
            for (int i = 0; i < table.getColumnCount(); i++) {
                sheet.autoSizeColumn(i);
            }

            // Save file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Excel File");
            fileChooser.setSelectedFile(new java.io.File("CustomersOrder.xlsx"));

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                FileOutputStream fileOut = new FileOutputStream(fileChooser.getSelectedFile());
                workbook.write(fileOut);
                fileOut.close();
                workbook.close();

                JOptionPane.showMessageDialog(this, "Excel file saved successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creating Excel file: " + e.getMessage());
        }
    }

    // Clear input fields
    private void clearFields() {
        txtOrderID.setText("");
        txtStaffID.setText("");
        txtItemID.setText("");
        txtQuantity.setText("");
        txtCustomerID.setText("");
        txtPurchaseDate.setText("");
        txtPaymentMethod.setText("");
        txtTotalPrice.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            addCustomerorder();
        } else if (e.getSource() == btnUpdate) {
            updateCustomerorder();
        } else if (e.getSource() == btnDelete) {
            deleteCustomer();
        } else if (e.getSource() == btnRefresh) {
            loadTableData();
            clearFields();
        } else if (e.getSource() == btnPrint) {
            printToExcel();
        } else if (e.getSource() == btnBack) {
            this.dispose();
            new Home(username, position);
        }
    }

}