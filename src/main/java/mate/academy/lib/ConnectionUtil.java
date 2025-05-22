package mate.academy.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/book_schema?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Chivo321$";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USERNAME);
        DB_PROPERTIES.put("password", PASSWORD);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Can't establish DB connection", e);
        }
    }
}
