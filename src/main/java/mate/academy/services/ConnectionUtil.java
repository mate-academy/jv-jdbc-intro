package mate.academy.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exceptions.DataProcessingException;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bookstore?serverTimezone=UTC";
    private static final String LOGIN_VALUE = "root";
    private static final String LOGIN_KEY = "user";
    private static final String PASSWORD_VALUE = "---SQLdno12";
    private static final String PASSWORD_KEY = "password";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DRIVER_ERROR = "Can't load JDBC driver";
    private static final String CONNECTION_ERROR = "Can't connect to database";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put(LOGIN_KEY, LOGIN_VALUE);
        DB_PROPERTIES.put(PASSWORD_KEY, PASSWORD_VALUE);

        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(DRIVER_ERROR, e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new DataProcessingException(CONNECTION_ERROR, e);
        }
    }
}
