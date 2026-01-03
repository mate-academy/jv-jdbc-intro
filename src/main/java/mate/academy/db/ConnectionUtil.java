package mate.academy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mysql";
    private static final Properties properties;

    static {
        properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "mateacad26");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, properties);
    }

}
