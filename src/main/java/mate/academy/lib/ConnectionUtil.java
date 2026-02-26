package mate.academy.lib;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String PROPS_FILE = "db.properties";
    private static String url;
    private static String user;
    private static String pass;

    static {
        try (InputStream in = ConnectionUtil.class.getClassLoader()
                .getResourceAsStream(PROPS_FILE)) {
            Properties props = new Properties();
            if (in == null) {
                throw new RuntimeException("Properties file not found: " + PROPS_FILE);
            }
            props.load(in);
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            pass = props.getProperty("db.password");
            // необов'язково, але можна для сумісності:
            // Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException /*| ClassNotFoundException*/ e) {
            throw new RuntimeException("Can't load DB properties", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get DB connection", e);
        }
    }
}
