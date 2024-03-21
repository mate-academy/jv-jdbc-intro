package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final Properties BD_PROPERTIES;

    static {
        BD_PROPERTIES = new Properties();
        BD_PROPERTIES.put("user", "root");
        BD_PROPERTIES.put("password", "Kondrateva1_");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cant load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, BD_PROPERTIES);
    }
}
