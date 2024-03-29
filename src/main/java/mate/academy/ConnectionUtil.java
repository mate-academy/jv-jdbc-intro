package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/jdcb-intro?serverTimezone=UTC";
    private static final String USER = "user";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String PASS_WORD = "password";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.setProperty(USER, USERNAME);
        DB_PROPERTIES.setProperty(PASSWORD, PASS_WORD);
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver: " + DRIVER, e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get connection to DB ", e );
        }
    }
}
