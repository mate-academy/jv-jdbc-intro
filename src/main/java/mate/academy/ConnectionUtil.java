package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/books";
    private static final Properties DB_PROPERTIES;
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "41456514aA";

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.setProperty("user", DB_USER);
        DB_PROPERTIES.setProperty("password", DB_PASSWORD);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load jdbc driver", e);
        }
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }

}
