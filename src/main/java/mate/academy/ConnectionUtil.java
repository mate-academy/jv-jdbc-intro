package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final Properties properties;

    static {
        // Loading driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Problems when loading JDBC mysql driver", e);
        }

        // Set properties for connection
        properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "Nabulio$2005");
    }

    public static Connection getConn() {
        try {
            return DriverManager.getConnection(DB_URL, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Problems when connecting to mysql DB test using JDBC", e);
        }
    }
}
