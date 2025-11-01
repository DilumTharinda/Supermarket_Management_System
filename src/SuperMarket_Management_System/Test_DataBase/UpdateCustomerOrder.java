package SuperMarket_Management_System.Test_DataBase;

import SuperMarket_Management_System.DataBase_Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateCustomerOrder {
    public static void main(String[] args){

        //SQl query
        String updateSQl = "UPDATE customer_order SET Payment_method = ? WHERE Order_ID = ?";

        try{
            //connect database
            Connection connection = DataBase_Connection.getConnection();

            //Prepared statement
            PreparedStatement preSt = connection.prepareStatement(updateSQl);


            preSt.setString(1, "Online");
            preSt.setString(2,"O001");

            //Execute
            int rowsAffected = preSt.executeUpdate();

            System.out.println(" updated successfully!");
            System.out.println("  Rows affected: " + rowsAffected);

            //close
            preSt.close();
            connection.close();


        }catch (SQLException e){
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
}
