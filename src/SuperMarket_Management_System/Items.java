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

public class Items extends JFrame implements ActionListener {

    // Buttons
    JButton btnAdd, btnUpdate, btnDelete, btnPrint, btnBack, btnRefresh;

    // Input fields
    JTextField txtItemID, txtItemName, txtQuantity, txtPrice;
    JComboBox<String> cmbCategory;

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
    public Items(String username, String position) {
        this.username = username;
        this.position = position;

        setTitle("Items Management - " + username + " (" + position + ")");

        // Set permissions based on position
        setPermissions();

        // Heading
        JLabel heading = new JLabel("Items Management");
        heading.setBounds(450, 10, 400, 40);
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
            case "supervisor":
            case "admin":
                canAdd = true;
                canUpdate = true;
                canDelete = true;
                break;

            case "cashier":
                canAdd = false;
                canUpdate = true; // Can update quantity/price
                canDelete = false;
                break;

            case "storekeeper":
                canAdd = true;
                canUpdate = true;
                canDelete = false; // Cannot delete items
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
        // Item ID
        JLabel lblItemID = new JLabel("Item ID:");
        lblItemID.setBounds(50, 70, 120, 25);
        lblItemID.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblItemID);

        txtItemID = new JTextField();
        txtItemID.setBounds(170, 70, 150, 25);
        add(txtItemID);

        // Item Name
        JLabel lblItemName = new JLabel("Item Name:");
        lblItemName.setBounds(350, 70, 120, 25);
        lblItemName.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblItemName);

        txtItemName = new JTextField();
        txtItemName.setBounds(470, 70, 250, 25);
        add(txtItemName);

        // Quantity
        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setBounds(50, 110, 120, 25);
        lblQuantity.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblQuantity);

        txtQuantity = new JTextField();
        txtQuantity.setBounds(170, 110, 150, 25);
        add(txtQuantity);

        // Category
        JLabel lblCategory = new JLabel("Category:");
        lblCategory.setBounds(350, 110, 120, 25);
        lblCategory.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblCategory);

        String[] categories = {"Select Category", "Beverages","Electronics","Accessories","Dairy", "Bakery", "Snacks",
                "Fruits", "Vegetables", "Meat", "Frozen", "Personal Care", "Household"};
        cmbCategory = new JComboBox<>(categories);
        cmbCategory.setBounds(470, 110, 150, 25);
        add(cmbCategory);

        // Price
        JLabel lblPrice = new JLabel("Price:");
        lblPrice.setBounds(650, 110, 120, 25);
        lblPrice.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblPrice);

        txtPrice = new JTextField();
        txtPrice.setBounds(770, 110, 150, 25);
        add(txtPrice);
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
        String[] columnNames = {"Item ID", "Item Name", "Quantity", "Category", "Price"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);

        // Add selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtItemID.setText(model.getValueAt(row, 0).toString());
                txtItemName.setText(model.getValueAt(row, 1).toString());
                txtQuantity.setText(model.getValueAt(row, 2).toString());
                cmbCategory.setSelectedItem(model.getValueAt(row, 3).toString());
                txtPrice.setText(model.getValueAt(row, 4).toString());
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
            String query = "SELECT * FROM items";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Clear existing data
            model.setRowCount(0);

            // Add rows to table
            while (rs.next()) {
                String itemID = rs.getString("Item_ID");
                String itemName = rs.getString("IName");
                int quantity = rs.getInt("Quantity");
                String category = rs.getString("Catergory");
                int price = rs.getInt("Price");

                model.addRow(new Object[]{itemID, itemName, quantity, category, price});
            }

            rs.close();
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    // Add new item
    private void addItem() {
        String itemID = txtItemID.getText().trim();
        String itemName = txtItemName.getText().trim();
        String quantityStr = txtQuantity.getText().trim();
        String category = cmbCategory.getSelectedItem().toString();
        String priceStr = txtPrice.getText().trim();

        if (itemID.isEmpty() || itemName.isEmpty() || quantityStr.isEmpty() ||
                category.equals("Select Category") || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            int price = Integer.parseInt(priceStr);

            Connection connection = DataBase_Connection.getConnection();
            String query = "INSERT INTO items (Item_ID, IName, Quantity, Catergory, Price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, itemID);
            pstmt.setString(2, itemName);
            pstmt.setInt(3, quantity);
            pstmt.setString(4, category);
            pstmt.setInt(5, price);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Item added successfully!");
                loadTableData();
                clearFields();
            }

            pstmt.close();
            connection.close();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity and Price must be numbers!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding item: " + e.getMessage());
        }
    }

    // Update item
    private void updateItem() {
        String itemID = txtItemID.getText().trim();
        String itemName = txtItemName.getText().trim();
        String quantityStr = txtQuantity.getText().trim();
        String category = cmbCategory.getSelectedItem().toString();
        String priceStr = txtPrice.getText().trim();

        if (itemID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an item to update!");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            int price = Integer.parseInt(priceStr);

            Connection connection = DataBase_Connection.getConnection();
            String query = "UPDATE items SET IName=?, Quantity=?, Catergory=?, Price=? WHERE Item_ID=?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, itemName);
            pstmt.setInt(2, quantity);
            pstmt.setString(3, category);
            pstmt.setInt(4, price);
            pstmt.setString(5, itemID);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Item updated successfully!");
                loadTableData();
                clearFields();
            }

            pstmt.close();
            connection.close();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity and Price must be numbers!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating item: " + e.getMessage());
        }
    }

    // Delete item
    private void deleteItem() {
        String itemID = txtItemID.getText().trim();

        if (itemID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an item to delete!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this item?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection connection = DataBase_Connection.getConnection();
                String query = "DELETE FROM items WHERE Item_ID=?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, itemID);

                int result = pstmt.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Item deleted successfully!");
                    loadTableData();
                    clearFields();
                }

                pstmt.close();
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting item: " + e.getMessage());
            }
        }
    }

    // Print to Excel
    private void printToExcel() {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Items");

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
            fileChooser.setSelectedFile(new java.io.File("Items.xlsx"));

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
        txtItemID.setText("");
        txtItemName.setText("");
        txtQuantity.setText("");
        cmbCategory.setSelectedIndex(0);
        txtPrice.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            addItem();
        } else if (e.getSource() == btnUpdate) {
            updateItem();
        } else if (e.getSource() == btnDelete) {
            deleteItem();
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