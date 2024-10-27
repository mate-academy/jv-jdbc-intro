package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

    private static final String DB_URL = "jdbc:mysQl://localhost:3306/test";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "kodek");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load MSQL Driver",e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
