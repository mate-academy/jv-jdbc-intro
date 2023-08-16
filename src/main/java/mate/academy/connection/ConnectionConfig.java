package mate.academy.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exceptions.DataProcessingException;

public class ConnectionConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/books";
    private static final Properties PROPERTIES;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            PROPERTIES = new Properties();
            PROPERTIES.put("user", "root");
            PROPERTIES.put("password", "1234");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Cannot load JDBC Driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, PROPERTIES);
    }
}
