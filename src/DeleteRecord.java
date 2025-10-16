import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRecord {

    public static void main(String[] args) {


        String deleteSQL = "DELETE FROM customer_order WHERE Order_ID = ?";

        try {

            Connection connection = DataBase_Connection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(deleteSQL);


            pstmt.setString(1, "O001");

            // Execute delete
            int rowsAffected = pstmt.executeUpdate();

            System.out.println("Record deleted successfully!");
            System.out.println("  Rows affected: " + rowsAffected);


            // Close resources
            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
}