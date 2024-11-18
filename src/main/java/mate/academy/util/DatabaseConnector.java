package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/mate_jdbc?serverTimezone=UTC";
    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        PROPERTIES.put("user", "root");
        PROPERTIES.put("password", "root");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load the JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, PROPERTIES);
    }
}