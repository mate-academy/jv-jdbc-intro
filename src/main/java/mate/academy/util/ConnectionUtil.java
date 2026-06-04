package mate.academy.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream = ConnectionUtil.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
                Class.forName("com.mysql.cj.jdbc.Driver");
            } else {
                throw new RuntimeException("application.properties not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Can't load database properties", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")
            );
        } catch (SQLException e) {
            throw new RuntimeException("Can't establish connection to DB", e);
        }
    }
}
