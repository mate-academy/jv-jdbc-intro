package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load driver", e);
        }
    }

    public static Connection getConnection(Properties properties, String dbUrl)
            throws SQLException {
        return DriverManager.getConnection(dbUrl, properties);
    }
}
