package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "henghfdf");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_PROPERTIES);
    }
}
