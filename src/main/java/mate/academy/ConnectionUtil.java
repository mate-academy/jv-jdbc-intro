package mate.academy;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL;
    private static final Properties DB_PROPERTIES;
    private static final String pathToConfig = "src/main/resources/configuration.properties";

    static {
        DB_PROPERTIES = new Properties();
        try {
            DB_PROPERTIES.load(new FileInputStream(pathToConfig));
        } catch (IOException e) {
            throw new RuntimeException("Cannot find file:" + pathToConfig,e);
        }
        DB_URL = DB_PROPERTIES.getProperty("url");
        try {
            Class.forName(DB_PROPERTIES.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot connect to Driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
