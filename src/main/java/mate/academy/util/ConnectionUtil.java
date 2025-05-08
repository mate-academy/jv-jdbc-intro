package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/test";
    private static final Properties DATABASE_PROPERTIES;

    static {
        DATABASE_PROPERTIES = new Properties();
        DATABASE_PROPERTIES.put("user", "root");
        DATABASE_PROPERTIES.put("password", "12345678");

        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load the JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_PROPERTIES);
    }
}
