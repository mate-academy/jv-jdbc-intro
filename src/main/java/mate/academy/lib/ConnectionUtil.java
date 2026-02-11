package mate.academy.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "StrongPassword!1";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", DB_USERNAME);
        DB_PROPERTIES.put("password", DB_PASSWORD);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_DRIVER, DB_PROPERTIES);
    }
}
