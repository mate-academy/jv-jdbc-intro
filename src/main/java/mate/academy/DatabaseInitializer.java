package mate.academy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import mate.academy.exception.DataProcessingException;

public class DatabaseInitializer {
    public void createTable() {
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            StringBuilder sqlCommands = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new FileReader("src/main/resources/init_db.sql"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sqlCommands.append(line).append(" ");
                }
            } catch (IOException e) {
                throw new DataProcessingException("Failed to read init_id.sql file: ", e);
            }

            statement.executeUpdate(sqlCommands.toString());
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create table", e);
        }
    }
}


