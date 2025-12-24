package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String URL =
            "jdbc:mysql://localhost:3306/book_warehouse?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Krzys2260!";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create connection to database", e);
        }
    }
}
