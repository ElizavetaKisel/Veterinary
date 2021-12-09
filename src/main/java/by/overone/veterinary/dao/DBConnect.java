package by.overone.veterinary.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static String URL = "jdbc:mysql://localhost:3306/veterinary";
    private static String dbUser = "root";
    private static String password = "root";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, dbUser, password);
    }
}
