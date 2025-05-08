package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/book_repository";
    private static final Properties DB_PROPERTIES;
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    private ConnectionUtil() {
    }

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "0000");
    }

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER_CLASS_NAME);
            return DriverManager.getConnection(URL, DB_PROPERTIES);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Can`t load JDBC driver.", ex);
        } catch (SQLException ex) {
            throw new RuntimeException("Can`t create a connection with database.");
        }
    }
}
