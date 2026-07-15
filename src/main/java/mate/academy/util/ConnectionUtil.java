package mate.academy.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionUtil {
    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream =
                     ConnectionUtil.class.getClassLoader()
                             .getResourceAsStream("db.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Can't load db.properties", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password"));
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to DB", e);
        }
    }
}
