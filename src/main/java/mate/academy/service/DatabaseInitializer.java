package mate.academy.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;
import mate.academy.ConnectionUtil;

public class DatabaseInitializer {
    private static final String INIT_SCRIPT_PATH = "init_db.sql";

    public static void init() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            System.out.println("Database connection established.");
            InputStream inputStream = DatabaseInitializer.class.getClassLoader()
                    .getResourceAsStream(INIT_SCRIPT_PATH);
            if (inputStream == null) {
                System.out.println("File not found! Make sure 'init_db.sql' "
                        + "is in src/main/resources.");
                return;
            }
            String script = new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .collect(Collectors.joining("\n"));
            String[] commands = script.split(";");
            try (Statement statement = connection.createStatement()) {
                for (String command : commands) {
                    statement.execute(command);
                }
                System.out.println("Database script executed successfully.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to database.", e);
        } catch (Exception e) {
            System.err.println("Database initialization failed!" + e);
        }
    }
}
