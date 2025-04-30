package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_example";
    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        PROPERTIES.put("user", "root");
        PROPERTIES.put("password", "roma042004");

    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create connection to DB.");
        }
    }

}
