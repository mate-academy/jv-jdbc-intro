package mate.academy.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try {
            Class.forName(JDBC_DRIVER);
            loadProperties();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    private static void loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(fis);
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");
        } catch (IOException e) {
            throw new RuntimeException("Can't load database config", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create DB connection", e);
        }
    }
}
