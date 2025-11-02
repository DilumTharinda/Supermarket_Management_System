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

public class Supplier extends JFrame implements ActionListener {

    // Buttons
    JButton btnAdd, btnUpdate, btnDelete, btnPrint, btnBack, btnRefresh;

    // Input fields
    JTextField txtSupplierID, txtFName, txtLName, txtContactNo, txtDistributorCompany;

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
    public Supplier(String username, String position) {
        this.username = username;
        this.position = position;

        setTitle("Supplier Management - " + username + " (" + position + ")");

        // Set permissions based on position
        setPermissions();

        // Heading
        JLabel heading = new JLabel("Supplier Management");
        heading.setBounds(420, 10, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 30));
        heading.setForeground(new Color(34, 139, 34));
        add(heading);

        // Input Panel
        createInputPanel();

        // Button Panel
        createButtonPanel();

        // Table Panel
        createTablePanel();

        // Load data from database
        loadTableData();

        // Window settings
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

            case "storekeeper":
                canAdd = true;
                canUpdate = true;
                canDelete = false;
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
        // Supplier ID
        JLabel lblSupplierID = new JLabel("Supplier ID:");
        lblSupplierID.setBounds(50, 70, 120, 25);
        lblSupplierID.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblSupplierID);

        txtSupplierID = new JTextField();
        txtSupplierID.setBounds(170, 70, 150, 25);
        add(txtSupplierID);

        // First Name
        JLabel lblFName = new JLabel("First Name:");
        lblFName.setBounds(350, 70, 120, 25);
        lblFName.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblFName);

        txtFName = new JTextField();
        txtFName.setBounds(470, 70, 150, 25);
        add(txtFName);

        // Last Name
        JLabel lblLName = new JLabel("Last Name:");
        lblLName.setBounds(650, 70, 120, 25);
        lblLName.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblLName);

        txtLName = new JTextField();
        txtLName.setBounds(770, 70, 150, 25);
        add(txtLName);

        // Contact No
        JLabel lblContactNo = new JLabel("Contact No:");
        lblContactNo.setBounds(50, 110, 120, 25);
        lblContactNo.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblContactNo);

        txtContactNo = new JTextField();
        txtContactNo.setBounds(170, 110, 150, 25);
        add(txtContactNo);

        // Distributor Company
        JLabel lblDistributorCompany = new JLabel("Company:");
        lblDistributorCompany.setBounds(350, 110, 120, 25);
        lblDistributorCompany.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblDistributorCompany);

        txtDistributorCompany = new JTextField();
        txtDistributorCompany.setBounds(470, 110, 450, 25);
        add(txtDistributorCompany);
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
        String[] columnNames = {"Supplier ID", "First Name", "Last Name", "Contact No", "Distributor Company"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);

        // Add selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtSupplierID.setText(model.getValueAt(row, 0).toString());
                txtFName.setText(model.getValueAt(row, 1).toString());
                txtLName.setText(model.getValueAt(row, 2).toString());
                txtContactNo.setText(model.getValueAt(row, 3).toString());
                txtDistributorCompany.setText(model.getValueAt(row, 4).toString());
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
            String query = "SELECT * FROM supplier";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Clear existing data
            model.setRowCount(0);

            // Add rows to table
            while (rs.next()) {
                String supplierID = rs.getString("Supplier_ID");
                String fName = rs.getString("FName");
                String lName = rs.getString("LName");
                String contactNo = rs.getString("Contact_NO");
                String distributorCompany = rs.getString("Distributor_Company");

                model.addRow(new Object[]{supplierID, fName, lName, contactNo, distributorCompany});
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    // Add new supplier
    private void addSupplier() {
        String supplierID = txtSupplierID.getText().trim();
        String fName = txtFName.getText().trim();
        String lName = txtLName.getText().trim();
        String contactNo = txtContactNo.getText().trim();
        String distributorCompany = txtDistributorCompany.getText().trim();

        if (supplierID.isEmpty() || fName.isEmpty() || lName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!");
            return;
        }

        try {
            Connection connection = DataBase_Connection.getConnection();
            String query = "INSERT INTO supplier (Supplier_ID, FName, LName, Contact_NO, Distributor_Company) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, supplierID);
            pstmt.setString(2, fName);
            pstmt.setString(3, lName);
            pstmt.setString(4, contactNo);
            pstmt.setString(5, distributorCompany);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Supplier added successfully!");
                loadTableData();
                clearFields();
            }

            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding supplier: " + e.getMessage());
        }
    }

    // Update supplier
    private void updateSupplier() {
        String supplierID = txtSupplierID.getText().trim();
        String fName = txtFName.getText().trim();
        String lName = txtLName.getText().trim();
        String contactNo = txtContactNo.getText().trim();
        String distributorCompany = txtDistributorCompany.getText().trim();

        if (supplierID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a supplier to update!");
            return;
        }

        try {
            Connection connection = DataBase_Connection.getConnection();
            String query = "UPDATE supplier SET FName=?, LName=?, Contact_NO=?, Distributor_Company=? WHERE Supplier_ID=?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, fName);
            pstmt.setString(2, lName);
            pstmt.setString(3, contactNo);
            pstmt.setString(4, distributorCompany);
            pstmt.setString(5, supplierID);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Supplier updated successfully!");
                loadTableData();
                clearFields();
            }

            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating supplier: " + e.getMessage());
        }
    }

    // Delete supplier
    private void deleteSupplier() {
        String supplierID = txtSupplierID.getText().trim();

        if (supplierID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a supplier to delete!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this supplier?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection connection = DataBase_Connection.getConnection();
                String query = "DELETE FROM supplier WHERE Supplier_ID=?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, supplierID);

                int result = pstmt.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Supplier deleted successfully!");
                    loadTableData();
                    clearFields();
                }

                pstmt.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting supplier: " + e.getMessage());
            }
        }
    }

    // Print to Excel
    private void printToExcel() {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Suppliers");

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
            fileChooser.setSelectedFile(new java.io.File("Suppliers.xlsx"));

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
        txtSupplierID.setText("");
        txtFName.setText("");
        txtLName.setText("");
        txtContactNo.setText("");
        txtDistributorCompany.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            addSupplier();
        } else if (e.getSource() == btnUpdate) {
            updateSupplier();
        } else if (e.getSource() == btnDelete) {
            deleteSupplier();
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