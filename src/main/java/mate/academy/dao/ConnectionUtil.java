package mate.academy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String JDBC_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/books_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "My786453120+";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", DB_USER);
        DB_PROPERTIES.put("password", DB_PASSWORD);

        try {
            Class.forName(JDBC_DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load jdbc.Driver: " + JDBC_DRIVER_CLASS_NAME, e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create a connection to DB: " + DB_URL, e);
        }
    }
}
