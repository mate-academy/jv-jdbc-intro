package mate.academy.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final Properties DB_PROPERTIES = new Properties();
    private static Connection connection;

    static {
        try (InputStream input = ConnectionUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find db.properties file");
            }
            DB_PROPERTIES.load(input);
            System.out.println("Loaded DB URL: " + DB_PROPERTIES.getProperty("db.url"));
            System.out.println("Loaded DB Username: " + DB_PROPERTIES.getProperty("db.username"));
        } catch (IOException ex) {
            throw new RuntimeException("Unable to load database properties file", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = DB_PROPERTIES.getProperty("db.url");
            String user = DB_PROPERTIES.getProperty("db.username");
            String password = DB_PROPERTIES.getProperty("db.password");

            if (url == null || user == null || password == null) {
                throw new RuntimeException("Database properties are not properly set");
            }

            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
}

