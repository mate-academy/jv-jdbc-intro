package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL
            = "jdbc:mysql://localhost:3306/book";
    private static final Properties DB_PROPERTIES;
    private static final String PASSWORD = "password";
    private static final String USER = "user";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "bookPass";
    private static final String CONNECTION_TO_DRIVER
            = "com.mysql.cj.jdbc.Driver";

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put(USER, USER_NAME);
        DB_PROPERTIES.put(PASSWORD, USER_PASSWORD);

        try {
            Class.forName(CONNECTION_TO_DRIVER);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Can't connecting to the driver", ex);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connected to database!", e);
        }
    }
}
