package mate.academy;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final Properties DB_PROPERTIES = new Properties();

    static {
        try (FileInputStream input = new FileInputStream("config.properties")) {
            DB_PROPERTIES.load(input);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Can not load configuration or JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = DB_PROPERTIES.getProperty("db.url");
        return DriverManager.getConnection(url, DB_PROPERTIES);
    }
}
