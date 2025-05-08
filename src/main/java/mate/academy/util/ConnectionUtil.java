package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exceptions.DataProcessingException;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/books_storage";
    private static final String DRIVER_URL = "com.mysql.cj.jdbc.Driver";
    private static final Properties DB_PROPERTIES;
    private static final String USER = "user";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_REALIZATION = "55555Qwz!";

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put(USER, USER_NAME);
        DB_PROPERTIES.put(PASSWORD, PASSWORD_REALIZATION);

        try {
            Class.forName(DRIVER_URL);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new DataProcessingException("Unable get connection to DB", e);
        }
    }
}
