package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionCreator {
    public static final String CANNOT_LOAD_DRIVER = "Cannot load the driver";
    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/jdbc_task";
    private static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final Properties dbProperties = new Properties();

    static {
        dbProperties.put("user", "root");
        dbProperties.put("password", "my pass");
        try {
            Class.forName(CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(CANNOT_LOAD_DRIVER, e);
        }
    }

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, dbProperties);
    }
}
