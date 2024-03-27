package mate.academy.connection.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final Properties DB_PROPERTIES;
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "715914qQ()");
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER_NAME);
            return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
