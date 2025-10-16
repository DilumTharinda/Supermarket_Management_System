import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadRecord {
    public static void main(String [] args){

        //Select table
        String customer = "SELECT * FROM customer";
        String customer_order = "SELECT * FROM customer_order";

        try{
            //Connect Database
            Connection connection = DataBase_Connection.getConnection();

            //Create Statement
            Statement statement1 = connection.createStatement();
            //Execute query and get result
            ResultSet customer_result = statement1.executeQuery(customer);

            /*

            ResultSet items_result = statement.executeQuery(items);
            ResultSet staff_result = statement.executeQuery(staff);
            ResultSet supplier_result = statement.executeQuery(supplier);
            ResultSet supplier_order_result = statement.executeQuery(supplier_order);

*/

            System.out.println("\n========== All Customer Records ==========");
            System.out.println("Customer_ID\t\tFirst Name\t\tLast Name\tContact_NO\t\tAddress");
            System.out.println("-------------------------------------------------------------------");

            while(customer_result.next()){
                String id = customer_result.getString("Customer_ID");
                String firstName = customer_result.getString("FName");
                String lastName = customer_result.getString("LName");
                String Contact_NO = customer_result.getString("Contact_NO");
                String Address = customer_result.getString("Address");

                // Print
                System.out.printf("\t%s\t\t\t%s\t\t\t%s\t%s\t\t%s%n",
                        id, firstName, lastName, Contact_NO, Address);
            }

            customer_result.close();
            statement1.close();

            //Table 2 -------------------------------------------------------------------
            Statement statement2 = connection.createStatement();
            ResultSet customer_order_result = statement2.executeQuery(customer_order);

            System.out.println("\n========== All Customer Order Records ==========");
            System.out.println("Order_ID\tStaff_ID\tItem_ID\tQuantity\tCustomer_ID\t\tPurchase_Date\tPayment_method\tTotal_Price");
            System.out.println("------------------------------------------------------------------------------------------------------");

            while(customer_order_result.next()){
                String Order_ID = customer_order_result.getString("Order_ID");
                String Staff_ID = customer_order_result.getString("Staff_ID");
                String Item_ID = customer_order_result.getString("Item_ID");
                int Quantity = customer_order_result.getInt("Quantity");
                String Customer_ID = customer_order_result.getString("Customer_ID");
                String Purchase_Date = customer_order_result.getString("Purchase_Date");
                String Payment_method = customer_order_result.getString("Payment_method");
                int Total_Price = customer_order_result.getInt("Total_Price");


                System.out.printf("\t%s\t\t\t%s\t\t\t%s\t\t%d\t\t\t%s\t\t\t%s\t\t\t%s\t\t\t%d%n",
                        Order_ID, Staff_ID, Item_ID, Quantity, Customer_ID, Purchase_Date, Payment_method, Total_Price);
            }

            customer_order_result.close();
            statement2.close();


            connection.close();
        }catch (SQLException e){
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
}
