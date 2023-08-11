package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL =  "jdbc:mysql://localhost:3306/my_first_library_db";
    private static final String MY_SQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_VALUE = "root";
    private static final String PASSWORD_VALUE = "1234";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USER_VALUE);
        DB_PROPERTIES.put("password", PASSWORD_VALUE);
        try {
            Class.forName(MY_SQL_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load: " + MY_SQL_DRIVER, e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
