package mate.academy.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exeption.DataProcessingException;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bookstore";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "12345678";
    private static final String FILED_USER = "user";
    private static final String FILED_PASSWORD = "password";
    private static final Properties DB_PROPERTIES;

    static {

        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put(FILED_USER, USER_NAME);
        DB_PROPERTIES.put(FILED_PASSWORD, USER_PASSWORD);

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Cannot load JDBC Driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL,DB_PROPERTIES);
    }
}
