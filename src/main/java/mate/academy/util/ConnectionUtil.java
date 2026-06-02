package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String URL =
            "jdbc:mysql://localhost:3306/book_store?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private ConnectionUtil() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(
                    "Can't esteblish conection to database", e);
        }
    }
}
