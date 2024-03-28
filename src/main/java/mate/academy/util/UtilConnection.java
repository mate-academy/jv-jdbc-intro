package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class UtilConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/book";
    private static final Properties PROPERTIES;
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";

    static {
        PROPERTIES = new Properties();
        PROPERTIES.put("user", LOGIN);
        PROPERTIES.put("password", PASSWORD);
    }

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER_CLASS_NAME);
            return DriverManager.getConnection(URL, PROPERTIES);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver ", e);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create a connection with database " + e);
        }
    }
}
