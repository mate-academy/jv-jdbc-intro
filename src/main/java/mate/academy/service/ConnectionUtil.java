package mate.academy.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/test?user=root";
    private static final Properties DB_PROPERTIES;
    private static final String USERNAME = System.getenv("DB_USERNAME");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USERNAME);
        DB_PROPERTIES.put("password", PASSWORD);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL,DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connection DriverManager");
        }
    }
}
