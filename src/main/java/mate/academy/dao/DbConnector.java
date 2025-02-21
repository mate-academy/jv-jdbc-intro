package mate.academy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DbConnector {
    public static final String ERROR_DURING_CONNECTION_CREATION =
            "Error during connection creation";
    private static final String USER = "root";
    private static final String PASSWORD = "Senkiv1905!";
    private final Properties properties;

    public DbConnector() {
        properties = new Properties();
        properties.put("user", USER);
        properties.put("password", PASSWORD);
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/mate", properties);
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_DURING_CONNECTION_CREATION, e);
        }
    }
}
