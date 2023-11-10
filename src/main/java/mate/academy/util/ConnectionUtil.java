package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.model.exception.DriverLoadingException;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/books";
    private static final Properties DB_PROPERTIES;
    private static final String DRIVER_LOADING_MESSAGE = "Can't load JDBC Driver";

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user","root");
        DB_PROPERTIES.put("password", "956874lv");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DriverLoadingException(DRIVER_LOADING_MESSAGE, e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
