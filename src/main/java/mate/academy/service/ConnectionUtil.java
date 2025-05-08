package mate.academy.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER = "User";
    private static final String PASSWORD = "Password";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USER);
        DB_PROPERTIES.put("password", PASSWORD);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
