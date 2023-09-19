package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String LIBRARY_URL = "jdbc:mysql://localhost:3306/library";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "12345678Mate");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cant load jdbc Driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(LIBRARY_URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Cant create connection " + LIBRARY_URL, e);
        }
    }
}
