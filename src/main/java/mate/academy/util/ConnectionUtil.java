package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/book_store?serverTimezone=UTC";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "";

    private ConnectionUtil() {
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    getProperty("db.url", "DB_URL", DEFAULT_URL),
                    getProperty("db.user", "DB_USER", DEFAULT_USERNAME),
                    getProperty("db.password", "DB_PASSWORD", DEFAULT_PASSWORD));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't establish DB connection", e);
        }
    }

    private static String getProperty(String propertyName, String envName, String defaultValue) {
        String propertyValue = System.getProperty(propertyName);
        return propertyValue == null ? System.getenv().getOrDefault(envName, defaultValue)
                : propertyValue;
    }
}
