package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER_PROPERTY = "user";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root1234";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER_CLASS_NAME);
            Properties dbProperties = new Properties();
            dbProperties.put(USER_PROPERTY, DB_USER);
            dbProperties.put(PASSWORD_PROPERTY, DB_PASSWORD);
            return DriverManager
                    .getConnection(DB_URL, dbProperties);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load JDBC driver", e);
        }
    }
}
