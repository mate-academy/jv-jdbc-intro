package mate.academy.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import mate.academy.exception.DataProcessingException;

public class InitDatabase {
    public static void executeInitScript(Connection connection) {
        String scriptPath = "src/main/resources/init_db.sql";
        try {
            String sql = new String(Files.readAllBytes(Paths.get(scriptPath)));
            String[] queries = sql.split(";");
            try (Statement statement = connection.createStatement()) {
                for (String query : queries) {
                    if (!query.trim().isEmpty()) {
                        statement.execute(query.trim());
                    }
                }
            } catch (SQLException e) {
                throw new DataProcessingException("Cannot execute init_db.sql query", e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot init DataBase", e);
        }
    }
}
