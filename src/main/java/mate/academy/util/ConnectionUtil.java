package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exceptions.DataProcessingException;

public class ConnectionUtil {
    private static final Properties DB_PROPERTIES;
    private static final String URL = "jdbc:mysql://localhost:3306/mate";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USER);
        DB_PROPERTIES.put("password", PASSWORD);
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Failed to load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_PROPERTIES);
    }
}
