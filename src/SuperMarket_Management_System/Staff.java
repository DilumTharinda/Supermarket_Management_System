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

public class Staff extends JFrame implements ActionListener {

    // Buttons
    JButton btnAdd, btnUpdate, btnDelete, btnPrint, btnBack, btnRefresh;

    // Input fields
    JTextField txtStaffID, txtFName, txtLName, txtContactNo, txtAddress;
    JComboBox<String> cmbPosition;


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
    public Staff(String username, String position) {
        this.username = username;
        this.position = position;

        setTitle("Staff Management - " + username + " (" + position + ")");


        setPermissions();


        JLabel heading = new JLabel("Staff Management");
        heading.setBounds(450, 10, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 30));
        heading.setForeground(new Color(220, 20, 60));
        add(heading);

        createInputPanel();
        generateStaffID();
        createButtonPanel();
        createTablePanel();
        loadTableData();


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
            case "assistant manager":
            case "supervisor":
            case "department manager":
            case "hr":
            case "admin":
            case "it":
            case "system admin":
                canAdd = true;
                canUpdate = true;
                canDelete = true;
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
        // Staff ID
        JLabel lblStaffID = new JLabel("Staff ID:");
        lblStaffID.setBounds(50, 70, 120, 25);
        lblStaffID.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblStaffID);

        txtStaffID = new JTextField();
        txtStaffID.setBounds(170, 70, 150, 25);
        txtStaffID.setEditable(false);
        txtStaffID.setBackground(Color.LIGHT_GRAY);
        add(txtStaffID);

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

        // Address
        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(350, 110, 120, 25);
        lblAddress.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblAddress);

        txtAddress = new JTextField();
        txtAddress.setBounds(470, 110, 250, 25);
        add(txtAddress);

        // Position
        JLabel lblPosition = new JLabel("Position:");
        lblPosition.setBounds(750, 110, 120, 25);
        lblPosition.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblPosition);

        String[] positions = {"Select Position", "Manager", "Assistant Manager", "Supervisor", "Cashier", "Storekeeper", "HR", "Admin", "Accountant", "System Admin"};
        cmbPosition = new JComboBox<>(positions);
        cmbPosition.setBounds(870, 110, 200, 25);
        add(cmbPosition);


    }

    // Create button panel
    private void createButtonPanel() {
        int yPos = 200;
        // Add Button
        btnAdd = new JButton("Add");
        btnAdd.setBounds(50, yPos, 100, 35);
        btnAdd.setBackground(new Color(34, 139, 34));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.addActionListener(this);
        btnAdd.setEnabled(canAdd);
        add(btnAdd);

        // Update Button
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(170, yPos, 100, 35);
        btnUpdate.setBackground(new Color(255, 140, 0));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFont(new Font("Arial", Font.BOLD, 14));
        btnUpdate.addActionListener(this);
        btnUpdate.setEnabled(canUpdate);
        add(btnUpdate);

        // Delete Button
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(290, 160, 100, 35);
        btnDelete.setBounds(290, yPos, 100, 35);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setBackground(new Color(220, 60, 40));
        btnDelete.setFont(new Font("Arial", Font.BOLD, 14));
        btnDelete.addActionListener(this);
        btnDelete.setEnabled(canDelete);
        add(btnDelete);

        // Refresh Button
        btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(410, yPos, 100, 35);
        btnRefresh.setBackground(new Color(70, 130, 180));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 14));
        btnRefresh.addActionListener(this);
        add(btnRefresh);

        // Print Button
        btnPrint = new JButton("Print Excel");
        btnPrint.setBounds(530, yPos, 120, 35);
        btnPrint.setBackground(new Color(128, 0, 128));
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setFont(new Font("Arial", Font.BOLD, 14));
        btnPrint.addActionListener(this);
        add(btnPrint);

        // Back Button
        btnBack = new JButton("Back");
        btnBack.setBounds(1020, yPos, 100, 35);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.addActionListener(this);
        add(btnBack);
    }

    private void createTablePanel() {
        String[] columnNames = {"Staff ID", "First Name", "Last Name", "Contact No", "Address", "Position"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtStaffID.setText(model.getValueAt(row, 0).toString());
                txtFName.setText(model.getValueAt(row, 1).toString());
                txtLName.setText(model.getValueAt(row, 2).toString());
                txtContactNo.setText(model.getValueAt(row, 3).toString());
                txtAddress.setText(model.getValueAt(row, 4).toString());
                cmbPosition.setSelectedItem(model.getValueAt(row, 5).toString());

            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 260, 1100, 360);
        add(scrollPane);
    }

    private void loadTableData() {
        try {
            Connection connection = DataBase_Connection.getConnection();
            String query = "SELECT * FROM staff";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            model.setRowCount(0);
            while (rs.next()) {
                String staffID = rs.getString("Staff_ID");
                String fName = rs.getString("FName");
                String lName = rs.getString("LName");
                String contactNo = rs.getString("Contact_NO");
                String address = rs.getString("Address");
                String position = rs.getString("Position");

                model.addRow(new Object[]{staffID, fName, lName, contactNo, address, position});
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void addStaff() {
        if(txtStaffID.getText().isEmpty()) generateStaffID();
        String staffID = txtStaffID.getText().trim();
        String fName = txtFName.getText().trim();
        String lName = txtLName.getText().trim();
        String contactNo = txtContactNo.getText().trim();
        String address = txtAddress.getText().trim();
        String position = cmbPosition.getSelectedItem().toString();

        if (staffID.isEmpty() || fName.isEmpty() || lName.isEmpty() || position.equals("Select Position")) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!");
            return;
        }
        if (!validateInput(fName, lName, contactNo, address)) {
            return;
        }

        try {
            Connection connection = DataBase_Connection.getConnection();
            String query = "INSERT INTO staff (Staff_ID, FName, LName, Contact_NO, Address, Position) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, staffID);
            pstmt.setString(2, fName);
            pstmt.setString(3, lName);
            pstmt.setString(4, contactNo);
            pstmt.setString(5, address);
            pstmt.setString(6, position);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Staff added successfully!");
                loadTableData();
                clearFields();
            }

            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding staff: " + e.getMessage());
        }
    }

    private void updateStaff() {
        String staffID = txtStaffID.getText().trim();
        String fName = txtFName.getText().trim();
        String lName = txtLName.getText().trim();
        String contactNo = txtContactNo.getText().trim();
        String address = txtAddress.getText().trim();
        String position = cmbPosition.getSelectedItem().toString();
        if (staffID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a staff member to update!");
            return;
        }
        if (!validateInput(fName, lName, contactNo, address)) {
            return;
        }
        try {
            Connection connection = DataBase_Connection.getConnection();
            String query = "UPDATE staff SET FName=?, LName=?, Contact_NO=?, Address=?, Position=?, username=?, password=? WHERE Staff_ID=?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, fName);
            pstmt.setString(2, lName);
            pstmt.setString(3, contactNo);
            pstmt.setString(4, address);
            pstmt.setString(5, position);
            pstmt.setString(6, staffID);
            pstmt.executeUpdate();
            int result = pstmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Staff updated successfully!");
                loadTableData();
                clearFields();
            }
            pstmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating staff: " + e.getMessage());
        }
    }

    // Delete staff
    private void deleteStaff() {
        String staffID = txtStaffID.getText().trim();

        if (staffID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a staff member to delete!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this staff member?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection connection = DataBase_Connection.getConnection();
                String query = "DELETE FROM staff WHERE Staff_ID=?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, staffID);

                int result = pstmt.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Staff deleted successfully!");
                    loadTableData();
                    clearFields();
                }

                pstmt.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting staff: " + e.getMessage());
            }
        }
    }

    // Print to Excel
    private void printToExcel() {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Staff");

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
            fileChooser.setSelectedFile(new java.io.File("Staff.xlsx"));

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

    private boolean validateInput(String fName, String lName, String contact, String address) {
        if (!fName.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(this, "First Name must contain letters only!");
            return false;
        }
        if (!lName.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(this, "Last Name must contain letters only!");
            return false;
        }

        if (address.length() < 5 || address.matches(".*[!@#$%^&*()].*")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid, meaningful Address.");
            return false;
        }

        if (!contact.matches("^07[014678]\\d{7}$")) {
            JOptionPane.showMessageDialog(this,
                    "Invalid Phone Number!\n" +
                            "- Must be 10 Digits\n" +
                            "- Must start with 077, 076, 074, 070, 071, or 078");
            return false;
        }

        return true;
    }
    private void generateStaffID() {
        try (Connection conn = DataBase_Connection.getConnection()) {
            String sql = "SELECT Staff_ID FROM staff ORDER BY Staff_ID DESC LIMIT 1";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String lastID = rs.getString("Staff_ID");
                int idNum = Integer.parseInt(lastID.substring(1));
                idNum=idNum+1;
                String newID = String.format("S%03d", idNum);
                txtStaffID.setText(newID);
            } else {
                txtStaffID.setText("S001");
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            txtStaffID.setText("S001");
        }
    }

    // Clear input fields
    private void clearFields() {
        txtStaffID.setText("");
        txtFName.setText("");
        txtLName.setText("");
        txtContactNo.setText("");
        txtAddress.setText("");
        cmbPosition.setSelectedIndex(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            addStaff();
        } else if (e.getSource() == btnUpdate) {
            updateStaff();
        } else if (e.getSource() == btnDelete) {
            deleteStaff();
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