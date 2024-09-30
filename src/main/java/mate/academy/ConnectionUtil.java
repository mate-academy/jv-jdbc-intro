package mate.academy;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static String password;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbc_intro";
    private static final String USERNAME = "root";
    private static final String PASSWORD_PATH = "src/main/resources/password.txt";
    private static final Properties DB_PROPERTIES;

    static {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(PASSWORD_PATH))) {
            password = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USERNAME);
        DB_PROPERTIES.put("password", password);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
