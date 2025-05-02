package mate.academy.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class ConnectionUtil {
    private static final int URL_INDEX = 0;
    private static final int LOGIN_INDEX = 1;
    private static final int PASSWORD_INDEX = 2;
    private static final List<String> credentials = read("credentials.txt");
    private static final String DB_URL = credentials.get(URL_INDEX);
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", credentials.get(LOGIN_INDEX));
        DB_PROPERTIES.put("password", credentials.get(PASSWORD_INDEX));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }

    private static List<String> read(String filePath) {
        List<String> credentials;
        try {
            credentials = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + filePath);
        }
        return credentials;
    }
}
