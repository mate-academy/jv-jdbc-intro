package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL =
            "jdbc:mysql://localhost:3306/book_store?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "fn1z_mate";

    private ConnectionUtil() {
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", USERNAME);
        dbProperties.put("password", PASSWORD);

        try {
            return DriverManager.getConnection(URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't establish connection to DB", e);
        }
    }
}
