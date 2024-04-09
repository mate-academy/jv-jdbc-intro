package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/books_db";
    public static final Properties DB_PROPERTIES;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String USER_NAME = "root";
    public static final String USER_PASSWORD = "Qwerty";

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USER_NAME);
        DB_PROPERTIES.put("password", USER_PASSWORD);

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver: " + DRIVER, e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }

}
