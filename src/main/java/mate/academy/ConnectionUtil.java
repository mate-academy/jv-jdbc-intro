package mate.academy;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL;
    private static final String PATH_TO_FILE = "src/main/resources/configuration.properties";

    private ConnectionUtil() {
    }

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(PATH_TO_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Cannot find file:" + PATH_TO_FILE,e);
        }
        DB_URL = properties.getProperty("url");
        try {
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot connect to Driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
