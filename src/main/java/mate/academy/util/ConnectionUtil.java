package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private static final Properties PROPERTIES = new Properties();

    static {
        PROPERTIES.put("user", USERNAME);
        PROPERTIES.put("password", PASSWORD);
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, PROPERTIES);
    }
}
