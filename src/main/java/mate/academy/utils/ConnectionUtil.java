package mate.academy.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345678";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DRIVER_PACKAGE = "com.mysql.cj.jdbc.Driver";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USERNAME);
        DB_PROPERTIES.put("password", PASSWORD);
        try {
            Class.forName(DRIVER_PACKAGE);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver :" + e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
