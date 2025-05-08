package mate.academy.connectionutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bookstore";
    private static final Properties DB_PROPERTIES;
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "Kate5819";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USER_NAME);
        DB_PROPERTIES.put("password", PASSWORD);
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
