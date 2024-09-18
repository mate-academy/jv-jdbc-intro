package mate.academy;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final Properties DB_PROPERTIES = new Properties();
    private static final String CONFIG_FILE = "config.properties";

    static {
        try (InputStream input = ConnectionUtil.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new RuntimeException(
                        "Configuration file '" + CONFIG_FILE + "' not found in classpath");
            }
            DB_PROPERTIES.load(input);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Cannot load configuration or JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = DB_PROPERTIES.getProperty("db.url");
        String user = DB_PROPERTIES.getProperty("db.user");
        String password = DB_PROPERTIES.getProperty("db.password");
        return DriverManager.getConnection(url, user, password);
    }
}
