package mate.academy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "12345";
    private static final String PATH_TO_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String PATH_TO_FILE = "src/main/resources/init_db.sql";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", DB_USERNAME);
        DB_PROPERTIES.put("password", DB_PASSWORD);

        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_TO_FILE));
                Statement statement = getConnection().createStatement()) {
            Class.forName(PATH_TO_DRIVER);
            StringBuilder sqlScript = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlScript.append(line).append("\n");
            }
            reader.close();
            statement.execute(sqlScript.toString());
        } catch (ClassNotFoundException | IOException | SQLException e) {
            throw new RuntimeException("Can't not load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
