package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL_PROPERTY = "db.url";
    private static final String DB_USER_PROPERTY = "db.user";
    private static final String DB_PASSWORD_PROPERTY = "db.password";
    private static final String DB_DRIVER_PROPERTY = "db.driver";
    private static final Properties dbProperties;

    static {
        dbProperties = new Properties();
        try {
            dbProperties.load(ConnectionUtil.class.getClassLoader()
                    .getResourceAsStream("db.properties"));
            Class.forName(dbProperties.getProperty(DB_DRIVER_PROPERTY));
        } catch (Exception e) {
            throw new RuntimeException("Can't load database properties from file", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                dbProperties.getProperty(DB_URL_PROPERTY),
                dbProperties.getProperty(DB_USER_PROPERTY),
                dbProperties.getProperty(DB_PASSWORD_PROPERTY)
        );
    }
}
