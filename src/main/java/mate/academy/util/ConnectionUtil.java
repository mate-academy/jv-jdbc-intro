package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String url;
    private static final Properties properties;

    static {
        url = "jdbc:mysql://localhost:3306/book_store?serverTimezone=UTC";
        properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "root1234");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load the jdbc driver class");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, properties);
    }
}
