package mate.academy.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String DRIVER_PATH = "com.mysql.cj.jdbc.Driver";
    private static final String DRIVER_LOAD_FAILURE_MESSAGE = "Driver loading failure!";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "");

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
