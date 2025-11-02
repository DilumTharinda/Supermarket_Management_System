package SuperMarket_Management_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase_Connection {

    //DataBase Details
    private static final String URL = "jdbc:mysql://localhost:3307/supermarket";//My local host
    private static final String USER = "root";
    private static final String PASSWARD = "1234";

    //connect to DataBase
    public static Connection getConnection() throws SQLException{
        try {
            //load jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL,USER,PASSWARD);
        }
        catch (ClassNotFoundException e){
            throw new SQLException("MYSQL JDBC driver not found!", e);
        }
    }

    //Test the Database
    public static void main(String[] args){
        try{
            Connection connection = getConnection();
            System.out.println("Database connected successfully!");
            connection.close();
        } catch (SQLException e){
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
