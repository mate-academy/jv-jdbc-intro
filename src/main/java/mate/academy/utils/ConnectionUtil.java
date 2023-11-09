package mate.academy.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
    private static final String DRIVER_LOAD_FAILURE_MESSAGE = "Driver loading failure!";
    private static final String PROPERTY_USER = "user";
    private static final String PROPERTY_PASSWORD = "password";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "QmZo_25107";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put(PROPERTY_USER, DB_USERNAME);
        DB_PROPERTIES.put(PROPERTY_PASSWORD, DB_PASSWORD);

        try {
            Class.forName(DRIVER_PATH);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(DRIVER_LOAD_FAILURE_MESSAGE, ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
