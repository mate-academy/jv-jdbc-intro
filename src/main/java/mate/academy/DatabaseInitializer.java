package mate.academy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import mate.academy.exception.DataProcessingException;

public class DatabaseInitializer {
    private static final String FILE_PATH = "src/main/resources/init_db.sql";

    public void createTable() {
        String sqlCommands = parseCommandFile(FILE_PATH);
        execute(sqlCommands);
        System.out.println("Table created successfully.");
    }

    private String parseCommandFile(String filePath) {
        StringBuilder sqlCommands = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sqlCommands.append(line.trim()).append("\n");
            }
        } catch (IOException e) {
            throw new DataProcessingException("Failed to read init_id.sql file: " + filePath, e);
        }
        return sqlCommands.toString();
    }

    private void execute(String sqlCommands) {
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCommands);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create table", e);
        }
    }
}
