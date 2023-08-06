package mate.academy.connection.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final Properties DB_PROPERTIES;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_DRIVER_EXCEPTION = "Cannot load JDBC driver";

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "Root1234");
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(JDBC_DRIVER_EXCEPTION, e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
