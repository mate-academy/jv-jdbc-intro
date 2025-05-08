package mate.academy.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/bookdatabase?serverTimezone=UTC";
    private static final String USER = "user";
    private static final String ROOT = "root";
    private static final String PASSWORD = "password";
    private static final String MY_PASSWORD = "password";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put(USER, ROOT);
        DB_PROPERTIES.put(PASSWORD, MY_PASSWORD);

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
