package SuperMarket_Management_System.Test_DataBase;

import SuperMarket_Management_System.DataBase_Connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class InputData {
    public static void main(String [] args){

        try{
            Connection connection = DataBase_Connection.getConnection();

            String insertCustomer = "INSERT INTO customer (Customer_ID, FName, LName, Contact_NO, Address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps1 = connection.prepareStatement(insertCustomer);
            ps1.setString(1, "C001");
            ps1.setString(2, "Kamal");
            ps1.setString(3, "Perera");
            ps1.setString(4, "0771234567");
            ps1.setString(5, "Colombo");
            ps1.executeUpdate();
            ps1.close();

            String insertOrder = "INSERT INTO customer_order (Order_ID, Staff_ID, Item_ID, Quantity, Customer_ID, Purchase_Date, Payment_method, Total_Price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps2 = connection.prepareStatement(insertOrder);
            ps2.setString(1, "O001");
            ps2.setString(2, "S001");
            ps2.setString(3, "I001");
            ps2.setInt(4, 2);
            ps2.setString(5, "C001");
            ps2.setString(6, "2025-10-16");
            ps2.setString(7, "Cash");
            ps2.setInt(8, 2700);
            ps2.executeUpdate();
            ps2.close();

            connection.close();
        }catch (SQLException e){
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
}
