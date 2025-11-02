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

public class Customer extends JFrame implements ActionListener {

    // Buttons
    JButton btnAdd, btnUpdate, btnDelete, btnPrint, btnBack, btnRefresh;

    // Input fields
    JTextField txtCustomerID, txtFName, txtLName, txtContactNo, txtAddress;

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
    public Customer(String username, String position) {
        this.username = username;
        this.position = position;

        setTitle("Customer Management - " + username + " (" + position + ")");

        // Set permissions based on position
        setPermissions();

        // Heading
        JLabel heading = new JLabel("Customer Management");
        heading.setBounds(400, 10, 400, 40);
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
        setSize(1280, 720);
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
        // Customer ID
        JLabel lblCustomerID = new JLabel("Customer ID:");
        lblCustomerID.setBounds(50, 70, 120, 25);
        lblCustomerID.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblCustomerID);

        txtCustomerID = new JTextField();
        txtCustomerID.setBounds(170, 70, 150, 25);
        add(txtCustomerID);

        // First Name
        JLabel lblFName = new JLabel("First Name:");
        lblFName.setBounds(50, 110, 120, 25);
        lblFName.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblFName);

        txtFName = new JTextField();
        txtFName.setBounds(170, 110, 150, 25);
        add(txtFName);

        // Last Name
        JLabel lblLName = new JLabel("Last Name:");
        lblLName.setBounds(350, 110, 120, 25);
        lblLName.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblLName);

        txtLName = new JTextField();
        txtLName.setBounds(470, 110, 150, 25);
        add(txtLName);

        // Contact No
        JLabel lblContactNo = new JLabel("Contact No:");
        lblContactNo.setBounds(650, 70, 120, 25);
        lblContactNo.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblContactNo);

        txtContactNo = new JTextField();
        txtContactNo.setBounds(770, 70, 150, 25);
        add(txtContactNo);

        // Address
        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(650, 110, 120, 25);
        lblAddress.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblAddress);

        txtAddress = new JTextField();
        txtAddress.setBounds(770, 110, 350, 25);
        add(txtAddress);
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
        String[] columnNames = {"Customer ID", "First Name", "Last Name", "Contact No", "Address"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);

        // Add selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtCustomerID.setText(model.getValueAt(row, 0).toString());
                txtFName.setText(model.getValueAt(row, 1).toString());
                txtLName.setText(model.getValueAt(row, 2).toString());
                txtContactNo.setText(model.getValueAt(row, 3).toString());
                txtAddress.setText(model.getValueAt(row, 4).toString());
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
            String query = "SELECT * FROM customer";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Clear existing data
            model.setRowCount(0);

            // Add rows to table
            while (rs.next()) {
                String customerID = rs.getString("Customer_ID");
                String fName = rs.getString("FName");
                String lName = rs.getString("LName");
                String contactNo = rs.getString("Contact_NO");
                String address = rs.getString("Address");

                model.addRow(new Object[]{customerID, fName, lName, contactNo, address});
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    // Add new customer
    private void addCustomer() {
        String customerID = txtCustomerID.getText().trim();
        String fName = txtFName.getText().trim();
        String lName = txtLName.getText().trim();
        String contactNo = txtContactNo.getText().trim();
        String address = txtAddress.getText().trim();

        if (customerID.isEmpty() || fName.isEmpty() || lName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!");
            return;
        }

        try {
            Connection connection = DataBase_Connection.getConnection();
            String query = "INSERT INTO customer (Customer_ID, FName, LName, Contact_NO, Address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, customerID);
            pstmt.setString(2, fName);
            pstmt.setString(3, lName);
            pstmt.setString(4, contactNo);
            pstmt.setString(5, address);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Customer added successfully!");
                loadTableData();
                clearFields();
            }

            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding customer: " + e.getMessage());
        }
    }

    // Update customer
    private void updateCustomer() {
        String customerID = txtCustomerID.getText().trim();
        String fName = txtFName.getText().trim();
        String lName = txtLName.getText().trim();
        String contactNo = txtContactNo.getText().trim();
        String address = txtAddress.getText().trim();

        if (customerID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a customer to update!");
            return;
        }

        try {
            Connection connection = DataBase_Connection.getConnection();
            String query = "UPDATE customer SET FName=?, LName=?, Contact_NO=?, Address=? WHERE Customer_ID=?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, fName);
            pstmt.setString(2, lName);
            pstmt.setString(3, contactNo);
            pstmt.setString(4, address);
            pstmt.setString(5, customerID);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Customer updated successfully!");
                loadTableData();
                clearFields();
            }

            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating customer: " + e.getMessage());
        }
    }

    // Delete customer
    private void deleteCustomer() {
        String customerID = txtCustomerID.getText().trim();

        if (customerID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this customer?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection connection = DataBase_Connection.getConnection();
                String query = "DELETE FROM customer WHERE Customer_ID=?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, customerID);

                int result = pstmt.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Customer deleted successfully!");
                    loadTableData();
                    clearFields();
                }

                pstmt.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting customer: " + e.getMessage());
            }
        }
    }

    // Print to Excel
    private void printToExcel() {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Customers");

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
            fileChooser.setSelectedFile(new java.io.File("Customers.xlsx"));

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
        txtCustomerID.setText("");
        txtFName.setText("");
        txtLName.setText("");
        txtContactNo.setText("");
        txtAddress.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            addCustomer();
        } else if (e.getSource() == btnUpdate) {
            updateCustomer();
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