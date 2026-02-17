package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_ROUTE = "jdbc:mysql://localhost:3306/academy";
    private static final Properties DB_PROPERTIES;
    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.setProperty("user", "root");
        DB_PROPERTIES.setProperty("password", "123123");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot connect to jdbc driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_ROUTE, DB_PROPERTIES);
    }
}
