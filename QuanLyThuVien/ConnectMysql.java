package QuanLyThuVien;

import java.sql.*;

public class ConnectMysql {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/library_oop";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1608a@";

    public static Connection getConnection() throws SQLException {
    //     return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        System.out.println("Connected to MySQL successfully");
        return connection;
    }
}
