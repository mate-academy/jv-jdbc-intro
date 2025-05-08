package mate.academy.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String PROPERTIES_FILE_PATH = "src/main/resources/db.properties";
    private static final String DATABASE_URL = "db.url";
    private static final String DATABASE_USER_NAME = "db.username";
    private static final String DATABASE_USER_PASSWORD = "db.password";
    private static final Properties properties = new Properties();

    static {
        readDatabaseFileProperties();
    }

    private static final void readDatabaseFileProperties() {
        try (InputStream inputStreamProperties = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(inputStreamProperties);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read properties file", e);
        }
    }

    public static Connection connectToDatabase() throws SQLException {
        String url = properties.getProperty(DATABASE_URL);
        String username = properties.getProperty(DATABASE_USER_NAME);
        String password = properties.getProperty(DATABASE_USER_PASSWORD);
        return DriverManager.getConnection(url, username, password);
    }
}
