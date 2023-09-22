package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "yamaha";
    private static final String CONNECTION_URL =
            "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            dbProperties.put("password", USER_PASSWORD);
            return DriverManager.getConnection(CONNECTION_URL, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can not create connection to the DB", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC Driver", e);
        }
    }
}
