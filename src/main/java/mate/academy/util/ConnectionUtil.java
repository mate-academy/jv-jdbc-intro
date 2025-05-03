package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/book_db";
    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        PROPERTIES.put("user", "root");
        PROPERTIES.put("password", "rootroot");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can not load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, PROPERTIES);
    }
}
