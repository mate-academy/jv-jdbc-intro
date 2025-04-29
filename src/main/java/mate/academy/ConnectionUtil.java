package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/books_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private static final Properties DB_PROPERTIES = new Properties();

    static {
        DB_PROPERTIES.put("user", DB_USER);
        DB_PROPERTIES.put("password", DB_PASSWORD);
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load SQL driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
