package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mate";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Qwerty123";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to database", e);
        }
    }
}
