package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/jdbc_intro?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USERNAME);
        DB_PROPERTIES.put("password", PASSWORD);

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load driver: " + DB_DRIVER, e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
