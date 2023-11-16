package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String DRIVERS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "user";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "password";
    private static final String USER_PASSWORD = "D1234?";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put(USER, USER_NAME);
        DB_PROPERTIES.put(PASSWORD, USER_PASSWORD);
        try {
            Class.forName(DRIVERS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not reload JDBC driver ", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
