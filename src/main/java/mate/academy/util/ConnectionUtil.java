package mate.academy.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String PROPERTIES_FILE = "db.properties";
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = ConnectionUtil.class.getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE)) {
            if (inputStream != null) {
                properties.load(inputStream);
                dbUrl = properties.getProperty("db.url");
                dbUser = properties.getProperty("db.user");
                dbPassword = properties.getProperty("db.password");
            } else {
                throw new RuntimeException(PROPERTIES_FILE + " not found");
            }
        } catch (IOException e) {
            throw new RuntimeException("Can`t read DataBase credentials.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot connect to database", e);
        }
    }
}
