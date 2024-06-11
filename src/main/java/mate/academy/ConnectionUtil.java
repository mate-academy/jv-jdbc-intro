package mate.academy;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/book";
    private static final Properties DB_PROPERIES;

    static {
        DB_PROPERIES = new Properties();
        DB_PROPERIES.put("user", "root");
        DB_PROPERIES.put("password", "root12345");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver", e);
        }
    }

    public static java.sql.Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERIES);
    }
}
