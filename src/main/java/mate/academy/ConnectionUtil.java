package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String DB_USERNAME = "";
    private static final String DB_PASSWORD = "";
    private static final String PATH_TO_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", DB_USERNAME);
        DB_PROPERTIES.put("password", DB_PASSWORD);

        try {
            Class.forName(PATH_TO_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't not load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
