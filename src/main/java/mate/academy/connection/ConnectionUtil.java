package mate.academy.connection;

import mate.academy.exeption.DataProcessingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "12345678";
    private static final Properties DB_PROPERTIES;

    static {

        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USER_NAME);
        DB_PROPERTIES.put("password", USER_PASSWORD);

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Cannot load JDBC Driver" ,e);
        }
    }

    public static Connection getConnection() throws SQLException {
       return DriverManager.getConnection(DB_URL,DB_PROPERTIES);
    }
}
