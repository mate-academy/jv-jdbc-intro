package mate.academy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jv_jdbc_intro";
    private static final Properties DB_PROPERTIES;
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "3McuPdh25LoKc1_";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USER_NAME);
        DB_PROPERTIES.put("password",PASSWORD);

        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Failed to connect to the Database."
                    + "Check the connection setting. Make sure that your URL is correct"
                    + DB_URL
            + "Verify user id and credentials", e);

        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL,DB_PROPERTIES);
    }
}
