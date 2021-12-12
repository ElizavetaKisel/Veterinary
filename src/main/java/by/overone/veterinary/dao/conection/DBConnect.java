package by.overone.veterinary.dao.conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static String URL = "jdbc:mysql://localhost:3306/veterinary";
    private static String dbUser = "root";
    private static String password = "root";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, dbUser, password);
    }
}
