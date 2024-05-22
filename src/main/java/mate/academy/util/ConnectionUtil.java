package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {

    private static Properties DB_PROPERTIES;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Root1234";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_URL =
            "jdbc:mysql://localhost:3306/book_storage?serverTimezone=UTC";

    static {
        loadDriver();
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot connected to JDBC_URL: " + JDBC_URL, e);
        }
    }

    private static void loadDriver() {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USERNAME);
        DB_PROPERTIES.put("password", PASSWORD);
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Cannot load JDBC driver", e);
        }
    }
}
