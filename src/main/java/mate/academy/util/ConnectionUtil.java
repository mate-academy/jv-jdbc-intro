package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String USER_NAME = "";
    private static final String USER_PASSWORD = "";
    //    TODO: write the DB login and password
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/books";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USER_NAME);
        DB_PROPERTIES.put("password", USER_PASSWORD);

        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_PROPERTIES);
    }
}
