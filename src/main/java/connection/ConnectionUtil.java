package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/test";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "rfrfirf");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load jdbc driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(CONNECTION_URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Can not create connection to DB", e);
        }
    }
}
