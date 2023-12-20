package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "admin");
        DB_PROPERTIES.put("password", "admin123");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/test", DB_PROPERTIES);

        } catch (SQLException e) {
            throw new DataProcessingException("Unable to connect to DB", e);
        }
    }
}
