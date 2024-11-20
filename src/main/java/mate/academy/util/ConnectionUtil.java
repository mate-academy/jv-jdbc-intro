package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import my.data.MyCredentials;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final Properties DB_PROPERTIES;
    private static final MyCredentials credentials = new MyCredentials();

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", credentials.getLogin());
        DB_PROPERTIES.put("password", credentials.getPassword());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
