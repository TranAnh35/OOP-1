package demo.Functions;

import java.sql.*;

public class MySqlConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/library_oop1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1608a@";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            System.out.println("Connected to MySQL successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading MySQL driver");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error connecting to MySQL");
            e.printStackTrace();
        }
        return connection;
    }
}
