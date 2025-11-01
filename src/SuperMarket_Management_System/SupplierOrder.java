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

public class SupplierOrder extends JFrame implements ActionListener {

    // Buttons
    JButton btnAdd, btnUpdate, btnDelete, btnPrint, btnBack, btnRefresh;

    // Input fields
    JTextField txtSupOrderID, txtSupplierID, txtItemID, txtQuantity, txtDate;

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
    public SupplierOrder(String username, String position) {
        this.username = username;
        this.position = position;

        setTitle("Supplier Order Management - " + username + " (" + position + ")");

        // Set permissions based on position
        setPermissions();

        // Heading
        JLabel heading = new JLabel("Supplier Order Management");
        heading.setBounds(370, 10, 460, 40);
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
            case "assistant manager":
            case "supervisor":
            case "department manager":
            case "hr":
            case "admin":
            case "accountant":
                canAdd = true;
                canUpdate = true;
                canDelete = true;
                break;

            case "storekeeper":
            case "stock clerk":
            case "supplier":
            case "delivery staff":
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
        // Supplier Order ID
        JLabel lblSupOrderID = new JLabel("Sup Order ID:");
        lblSupOrderID.setBounds(50, 70, 120, 25);
        lblSupOrderID.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblSupOrderID);

        txtSupOrderID = new JTextField();
        txtSupOrderID.setBounds(170, 70, 150, 25);
        add(txtSupOrderID);

        // Supplier ID
        JLabel lblSupplierID = new JLabel("Supplier ID:");
        lblSupplierID.setBounds(350, 70, 120, 25);
        lblSupplierID.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblSupplierID);

        txtSupplierID = new JTextField();
        txtSupplierID.setBounds(470, 70, 150, 25);
        add(txtSupplierID);

        // Item ID
        JLabel lblItemID = new JLabel("Item ID:");
        lblItemID.setBounds(650, 70, 120, 25);
        lblItemID.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblItemID);

        txtItemID = new JTextField();
        txtItemID.setBounds(770, 70, 200, 25);
        add(txtItemID);

        // Quantity
        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setBounds(50, 110, 120, 25);
        lblQuantity.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblQuantity);

        txtQuantity = new JTextField();
        txtQuantity.setBounds(170, 110, 150, 25);
        add(txtQuantity);

        // Date
        JLabel lblDate = new JLabel("Date:");
        lblDate.setBounds(350, 110, 120, 25);
        lblDate.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblDate);

        txtDate = new JTextField();
        txtDate.setBounds(470, 110, 150, 25);
        add(txtDate);
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
        String[] columnNames = {"Sup Order ID", "Supplier ID", "Item ID", "Quantity", "Date"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);

        // Add selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtSupOrderID.setText(model.getValueAt(row, 0).toString());
                txtSupplierID.setText(model.getValueAt(row, 1).toString());
                txtItemID.setText(model.getValueAt(row, 2).toString());
                txtQuantity.setText(model.getValueAt(row, 3).toString());
                txtDate.setText(model.getValueAt(row, 4).toString());
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
            String query = "SELECT * FROM supplier_order";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Clear existing data
            model.setRowCount(0);

            // Add rows to table
            while (rs.next()) {
                String supOrderID = rs.getString("Sup_Order_ID");
                String supplierID = rs.getString("Supplier_ID");
                String itemID = rs.getString("Item_ID");
                int quantity = rs.getInt("Quantity");
                String date = rs.getString("Date");

                model.addRow(new Object[]{supOrderID, supplierID, itemID, quantity, date});
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    // Add new supplier order
    private void addSupplierOrder() {
        String supOrderID = txtSupOrderID.getText().trim();
        String supplierID = txtSupplierID.getText().trim();
        String itemID = txtItemID.getText().trim();
        String quantity = txtQuantity.getText().trim();
        String date = txtDate.getText().trim();

        if (supOrderID.isEmpty() || supplierID.isEmpty() || itemID.isEmpty() || quantity.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!");
            return;
        }

        try {
            Connection connection = DataBase_Connection.getConnection();
            String query = "INSERT INTO supplier_order (Sup_Order_ID, Supplier_ID, Item_ID, Quantity, Date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, supOrderID);
            pstmt.setString(2, supplierID);
            pstmt.setString(3, itemID);
            pstmt.setInt(4, Integer.parseInt(quantity));
            pstmt.setString(5, date);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Supplier order added successfully!");
                loadTableData();
                clearFields();
            }

            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding supplier order: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a valid number!");
        }
    }

    // Update supplier order
    private void updateSupplierOrder() {
        String supOrderID = txtSupOrderID.getText().trim();
        String supplierID = txtSupplierID.getText().trim();
        String itemID = txtItemID.getText().trim();
        String quantity = txtQuantity.getText().trim();
        String date = txtDate.getText().trim();

        if (supOrderID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a supplier order to update!");
            return;
        }

        try {
            Connection connection = DataBase_Connection.getConnection();
            String query = "UPDATE supplier_order SET Supplier_ID=?, Item_ID=?, Quantity=?, Date=? WHERE Sup_Order_ID=?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, supplierID);
            pstmt.setString(2, itemID);
            pstmt.setInt(3, Integer.parseInt(quantity));
            pstmt.setString(4, date);
            pstmt.setString(5, supOrderID);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Supplier order updated successfully!");
                loadTableData();
                clearFields();
            }

            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating supplier order: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a valid number!");
        }
    }

    // Delete supplier order
    private void deleteSupplierOrder() {
        String supOrderID = txtSupOrderID.getText().trim();

        if (supOrderID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a supplier order to delete!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this supplier order?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection connection = DataBase_Connection.getConnection();
                String query = "DELETE FROM supplier_order WHERE Sup_Order_ID=?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, supOrderID);

                int result = pstmt.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Supplier order deleted successfully!");
                    loadTableData();
                    clearFields();
                }

                pstmt.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting supplier order: " + e.getMessage());
            }
        }
    }

    // Print to Excel
    private void printToExcel() {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("SupplierOrders");

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
            fileChooser.setSelectedFile(new java.io.File("SupplierOrders.xlsx"));

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
        txtSupOrderID.setText("");
        txtSupplierID.setText("");
        txtItemID.setText("");
        txtQuantity.setText("");
        txtDate.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            addSupplierOrder();
        } else if (e.getSource() == btnUpdate) {
            updateSupplierOrder();
        } else if (e.getSource() == btnRefresh) {
            loadTableData();
            clearFields();
        } else if (e.getSource() == btnDelete) {
            deleteSupplierOrder();
        } else if (e.getSource() == btnPrint) {
            printToExcel();
        } else if (e.getSource() == btnBack) {
            this.dispose();
            new Home(username, position);
        }
    }


}