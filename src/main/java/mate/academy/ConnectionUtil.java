package mate.academy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/learning";
    private static final Properties DB_PROPERTIES;
    private static final String INIT_FILE_PATH = "src/main/resourses/init_db.sql";
    private static String init_script;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "DFHdfh15%");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver ", e);
        }
        createTable();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }

    private static void createTable() {
        File initFile = new File(INIT_FILE_PATH);
        try {
            init_script = Files.readAllLines(initFile.toPath()).stream()
                    .reduce("", (sum, string) -> sum + string + System.lineSeparator())
                    .trim();
        } catch (IOException e) {
            throw new RuntimeException("Invalid file or file path " + INIT_FILE_PATH, e);
        }
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_PROPERTIES)) {
            PreparedStatement statement = connection.prepareStatement(init_script);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection. Check input data.", e);
        }
    }
}
