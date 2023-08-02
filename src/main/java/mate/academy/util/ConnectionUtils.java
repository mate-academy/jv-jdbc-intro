package mate.academy.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtils {
    private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_PROPERTIES_FILEPATH = "src/main/resources/db.properties";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbc_intro";

    private static final Properties properties;

    private ConnectionUtils() {
    }

    static {
        try {
            Class.forName(MYSQL_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load driver", e);
        }

        try (InputStream inputStream = new FileInputStream(DB_PROPERTIES_FILEPATH)) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(
                    "DB properties file not found, file=" + DB_PROPERTIES_FILEPATH, e
            );
        } catch (IOException e) {
            throw new RuntimeException("Cannot load DB properties", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot establish new connection", e);
        }
    }
}
