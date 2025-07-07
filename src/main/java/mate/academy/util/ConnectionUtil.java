package mate.academy.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_PROPERTIES_FILE = "db.properties";
    private static final String DB_URL_PROPERTY = "db.url";
    private static final String DB_USER_PROPERTY = "db.user";
    private static final String DB_PASSWORD_PROPERTY = "db.password";

    private static final Properties dbProperties = new Properties();

    static {
        try (InputStream input = ConnectionUtil.class.getClassLoader()
                .getResourceAsStream(DB_PROPERTIES_FILE)) {

            if (input == null) {
                throw new RuntimeException(
                        "Can't find database properties file " + DB_PROPERTIES_FILE);
            }

            dbProperties.load(input);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException e) {
            throw new RuntimeException("Can't read database properties from file "
                    + DB_PROPERTIES_FILE, e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load jdbc driver", e);
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
