package mate.academy.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {

    private static final Properties DB_PROPERTIES = new Properties();
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        try (InputStream input = ConnectionUtil.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new DataProcessingException("Unable to find db.properties", null);
            }
            DB_PROPERTIES.load(input);
            URL = DB_PROPERTIES.getProperty("db.url");
            USER = DB_PROPERTIES.getProperty("db.user");
            PASSWORD = DB_PROPERTIES.getProperty("db.password");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException | ClassNotFoundException e) {
            throw new DataProcessingException("Can't load database properties", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't establish connection to database", e);
        }
    }
}
