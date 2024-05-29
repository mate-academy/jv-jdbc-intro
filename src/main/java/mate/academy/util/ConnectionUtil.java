package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/mydatabase?serverTimezone=UTC";
    private static final Properties DB_PROPERTIES;
    private static Connection connection;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "mary");
        DB_PROPERTIES.put("password", "1905Z");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver", e);
        }
    }

    private ConnectionUtil() {

    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_PROPERTIES);
        }
        return connection;
    }
}
