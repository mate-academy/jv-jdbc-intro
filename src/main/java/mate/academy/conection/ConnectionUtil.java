package mate.academy.conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static final String DB_PROPERTY_NAME = "user";
    public static final String DB_PROPERTY_PASSWORD = "password";
    public static final String USER_NAME = "root";
    public static final String USER_PASSWORD = "123sqlBod321";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/book";
    public static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put(DB_PROPERTY_NAME, USER_NAME);
        DB_PROPERTIES.put(DB_PROPERTY_PASSWORD, USER_PASSWORD);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("can't load a jdbc driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("can't connect to DB", e);
        }
    }
}
