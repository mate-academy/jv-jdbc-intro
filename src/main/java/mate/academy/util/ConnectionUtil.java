package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String DB_LOCATION = "jdbc:mysql://localhost:3306/bookstore";
    private static final Properties DB_PROPERTIES;
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "741252369Wr?";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USER_NAME);
        DB_PROPERTIES.put("password", PASSWORD);
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Failed to load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_LOCATION, DB_PROPERTIES);
    }
}
