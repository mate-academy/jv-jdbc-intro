package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", System.getenv("DB_USERNAME"));
        DB_PROPERTIES.put("password", System.getenv("DB_PASSWORD"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot establish DB connection", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager
                .getConnection(System.getenv("DB_URL"), DB_PROPERTIES);
    }
}
