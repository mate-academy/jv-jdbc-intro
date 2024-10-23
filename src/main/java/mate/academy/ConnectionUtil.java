package mate.academy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import mate.academy.exceptions.DataProcessingException;

public class ConnectionUtil {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (user == null || password == null) {
            throw new DataProcessingException(
                "Database credentials are not set in environment variables", null);
        }

        DB_PROPERTIES.put("user", user);
        DB_PROPERTIES.put("password", password);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            initDatabase();
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't find driver", e);
        }
    }

    private ConnectionUtil() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }

    private static void initDatabase() {
        String sql = readSqlFile("init_db.sql");
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            String[] queries = sql.split(";");

            for (String query : queries) {
                query = query.trim();
                if (!query.isEmpty()) {
                    statement.execute(query);
                }
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Failed to initialize the database", e);
        }
    }

    private static String readSqlFile(String fileName) {
        try {
            Path path = Paths.get(Main.class.getClassLoader().getResource(fileName).toURI());
            return Files.readString(path);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to read SQL file", e);
        }
    }
}
