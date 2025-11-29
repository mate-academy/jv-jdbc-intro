package mate.academy.lib;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_PROPERTIES_FILE = "db.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream = ConnectionUtil.class
                .getClassLoader()
                .getResourceAsStream(DB_PROPERTIES_FILE)) {
            if (inputStream == null) {
                throw new RuntimeException("Properties file " + DB_PROPERTIES_FILE + " not found");
            }
            properties.load(inputStream);

            String driver = properties.getProperty("db.driver");
            if (driver == null) {
                throw new RuntimeException("Property db.driver is missing in db.properties");
            }
            Class.forName(driver);
        } catch (Exception e) {
            throw new RuntimeException("Can't load DB properties or driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");
        return DriverManager.getConnection(url, user, password);
    }
}
