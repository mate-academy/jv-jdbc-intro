package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Books";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put(USER, "root");
        DB_PROPERTIES.put(PASSWORD, "TelegramBOT");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load jdbc driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Can't establish connection", e);
        }
    }
}
