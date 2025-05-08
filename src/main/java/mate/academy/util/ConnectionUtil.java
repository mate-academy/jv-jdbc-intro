package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/book";
    private static final String DB_TYPE_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "123456789!");

        try {
            Class.forName(DB_TYPE_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JBDC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Can not connect to database", e);
        }
    }
}
