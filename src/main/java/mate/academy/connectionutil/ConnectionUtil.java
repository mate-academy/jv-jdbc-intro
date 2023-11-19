package mate.academy.connectionutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static final String CANNOT_LOAD_DRIVER = "Cannot load the driver";
    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306";
    private static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final Properties dbProperties = new Properties();
    private static final String USER = "";
    private static final String PASSWORD = "";

    static {
        dbProperties.put("user", USER);
        dbProperties.put("password", PASSWORD);
        try {
            Class.forName(CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(CANNOT_LOAD_DRIVER, e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, dbProperties);
    }
}
