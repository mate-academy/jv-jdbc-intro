package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mate_academy_hibernate";
    private static final Properties DB_PROPERTIES;
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String PROPERTY_USER = "user";
    private static final String PROPERTY_PASSWORD = "password";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "root";

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put(PROPERTY_USER, USER_NAME);
        DB_PROPERTIES.put(PROPERTY_PASSWORD, USER_PASSWORD);

        try {
            Class.forName(MYSQL_JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
