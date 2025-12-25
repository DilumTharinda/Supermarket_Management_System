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

public class Users extends JFrame implements ActionListener {

    // Buttons
    JButton btnAdd, btnUpdate, btnDelete, btnPrint, btnBack, btnRefresh;

    // Input fields
    JTextField txtStaffID, txtUsername;
    JPasswordField txtPassword;
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
    boolean isAdmin = false;

    //Constructor
    public Users(String username, String position) {
        this.username = username;
        this.position = (position != null) ? position : "Guest";

        String realPosition = fetchPositionFromDB(username);

        if (!realPosition.isEmpty()) {
            this.position = realPosition;
        } else {
            // Fallback if DB fetch fails (or use passed position)
            this.position = position;
        }

        this.isAdmin = this.position.equalsIgnoreCase("admin");

        setTitle("User Management - " + username + " (" + this.position + ")");

        // Set permissions based on the DERIVED position
        setPermissions();

        // Heading
        JLabel heading = new JLabel("User Management");
        heading.setBounds(400, 10, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 30));
        heading.setForeground(new Color(220, 20, 60));
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
            case "it":
            case "admin":
            case "assistantmanager":
                canAdd = true;
                canUpdate = true;
                canDelete = true;
                break;

            case "supervisor":
            case "hr":
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
        // ID
        JLabel lbStafflID = new JLabel("Satff ID:");
        lbStafflID.setBounds(50, 70, 120, 25);
        lbStafflID.setFont(new Font("Arial", Font.BOLD, 14));
        add(lbStafflID);

        txtStaffID = new JTextField();
        txtStaffID .setBounds(170, 70, 150, 25);
        txtStaffID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                autoGenerateUsername();
            }
        });
        add(txtStaffID );

        // Username
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(350, 70, 120, 25);
        lblUsername.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(470, 70, 200, 25);
        txtUsername.setEditable(false);
        add(txtUsername);

        // Password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(700, 70, 120, 25);
        lblPassword.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(820, 70, 200, 25);
        add(txtPassword);

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
        String[] columnNames = {"Staff_ID", "Username", "Password"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtStaffID.setText(model.getValueAt(row, 0).toString()); // Staff ID
                txtUsername.setText(model.getValueAt(row, 1).toString());
                txtPassword.setText(model.getValueAt(row, 2).toString());
            }
        });


        table.getColumnModel().getColumn(2).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // 1. Let Java handle the standard stuff (colors, selection highlighting)
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // 2. Apply your Custom Logic
                if (isAdmin) {
                    // If Admin: Show the actual password (value)
                    setText(value != null ? value.toString() : "");
                } else {
                    // If NOT Admin: Show dots
                    setText("••••••••");
                }

                return this;
            }
        });

        // Add selection listener


        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 220, 1100, 400);
        add(scrollPane);
    }

    // Load data from database
    private void loadTableData() {
        try {
            Connection connection = DataBase_Connection.getConnection();
            String query = "SELECT * FROM user";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Clear existing data
            model.setRowCount(0);

            // Add rows to table
            while (rs.next()) {
                String staffID = rs.getString("Staff_ID");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String displayPassword;
                if (isAdmin) {
                    displayPassword = password; // Admins see real password
                } else {
                    displayPassword = "●●●●●●"; // Others see dots
                }

                model.addRow(new Object[]{staffID, username, displayPassword});
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    // Add new user
    private void addUser() {
        String staffID = txtStaffID.getText().trim();
        String username = txtUsername.getText().trim();
        String password = String.valueOf(txtPassword.getPassword()).trim();

        if (staffID.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!");
            return;
        }
        if (!isValidPassword(password)) {
            JOptionPane.showMessageDialog(this,
                    "Password is too weak!\nIt must contain:\n- At least 1 Uppercase Letter\n- At least 1 Number\n- At least 1 Special Character",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!isStaffIdValid(staffID)) {
            JOptionPane.showMessageDialog(this, "Error: Staff ID '" + staffID + "' does not exist in the Staff database.\nPlease add the Staff member first.");
            return; // Stop the process
        }

        try {
            Connection connection = DataBase_Connection.getConnection();
            String query = "INSERT INTO user (Staff_ID, username, password) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, staffID);
            pstmt.setString(2, username);
            pstmt.setString(3, password);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "User added successfully!");
                loadTableData();
                clearFields();
            }

            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding user: " + e.getMessage());
            if(e.getMessage().contains("Constraint")) {
                JOptionPane.showMessageDialog(this, "Error: Staff ID may not exist or already has a user account.");
            } else {
                JOptionPane.showMessageDialog(this, "Error adding user: " + e.getMessage());
            }
        }

    }

    // Update user
    private void updateUser() {
        String staffID = txtStaffID.getText().trim();
        String username = txtUsername.getText().trim();
        String password = String.valueOf(txtPassword.getPassword()).trim();
        if (!isAdmin && password.contains("●")) {
            JOptionPane.showMessageDialog(this, "Security: You must enter a NEW password to update this user.");
            txtPassword.setText(""); // Clear it so they can type
            return;
        }

        if (staffID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a user to update!");
            return;
        }
        if (!password.contains("●")) {
            if (!isValidPassword(password)) {
                JOptionPane.showMessageDialog(this,
                        "Password is too weak!\nIt must contain:\n- At least 1 Uppercase Letter\n- At least 1 Number\n- At least 1 Special Character",
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        try {
            Connection connection = DataBase_Connection.getConnection();
            String query = "UPDATE user SET username=?, password=? WHERE Staff_ID=?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, staffID);


            int result = pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "User updated successfully!");
                loadTableData();
                clearFields();
            }

            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating user: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID format!");
        }
    }

    // Delete user
    private void deleteUser() {
        String staffID = txtStaffID.getText().trim();

        if (staffID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this user?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection connection = DataBase_Connection.getConnection();
                String query = "DELETE FROM user WHERE id=?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, staffID);

                int result = pstmt.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "User deleted successfully!");
                    loadTableData();
                    clearFields();
                }

                pstmt.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting user: " + e.getMessage());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid ID format!");
            }
        }
    }

    // Print to Excel
    private void printToExcel() {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Users");

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
            fileChooser.setSelectedFile(new java.io.File("Users.xlsx"));

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
        txtStaffID.setText("");
        txtUsername.setText("");
        txtPassword.setText("");

    }
    private boolean isValidPassword(String password) {
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            return false;
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            return false;
        }

        return true;
    }
    private boolean isStaffIdValid(String staffID) {
        boolean exists = false;
        try (Connection conn = DataBase_Connection.getConnection()) {
            String sql = "SELECT Staff_ID FROM staff WHERE Staff_ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, staffID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                exists = true; // Found it!
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
    private String fetchPositionFromDB(String username) {
        String dbPosition = "";
        try (Connection conn = DataBase_Connection.getConnection()) {
            String sql = "SELECT s.Position FROM staff s " +
                    "INNER JOIN user u ON s.Staff_ID = u.Staff_ID " +
                    "WHERE u.username = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                dbPosition = rs.getString("Position");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbPosition;
    }
    private String fetchStaffPosition(String staffID) {
        String pos = null;
        try (Connection conn = DataBase_Connection.getConnection()) {
            String sql = "SELECT Position FROM staff WHERE Staff_ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, staffID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pos = rs.getString("Position");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pos;
    }
    private void autoGenerateUsername() {
        String id = txtStaffID.getText().trim();
        if (id.isEmpty()) return;
        String staffPos = fetchStaffPosition(id);
        if (staffPos != null) {
            String cleanPos = staffPos.replace(" ", "");
            String autoUser = id + "_" + cleanPos;
            txtUsername.setText(autoUser);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            addUser();
        } else if (e.getSource() == btnUpdate) {
            updateUser();
        } else if (e.getSource() == btnDelete) {
            deleteUser();
        } else if (e.getSource() == btnRefresh) {
            loadTableData();
            clearFields();
        } else if (e.getSource() == btnPrint) {
            printToExcel();
        } else if (e.getSource() == btnBack) {
            this.dispose(); // Close current window
            new Home(username, position); // Return to Home screen
        }
    }
    }


