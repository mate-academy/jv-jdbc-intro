package mate.academy.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final Properties DB_PROPERTIES = new Properties();
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;

    static {
        try (InputStream input = ConnectionUtil.class.getClassLoader()
                .getResourceAsStream("db/db.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find db.properties in db package");
            }
            DB_PROPERTIES.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database properties", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        dbUrl = DB_PROPERTIES.getProperty("db.url");
        dbUser = DB_PROPERTIES.getProperty("db.user");
        dbPassword = DB_PROPERTIES.getProperty("db.password");

        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
