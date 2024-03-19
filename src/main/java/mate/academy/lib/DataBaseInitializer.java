package mate.academy.lib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import mate.academy.ConnectionUtil;
import mate.academy.exceptions.DataProcessingException;

public class DataBaseInitializer {
    public static void initializeDatabase() {
        StringBuilder script = new StringBuilder();
        try (Connection connection = ConnectionUtil.getConnection();
                BufferedReader reader = new BufferedReader(
                        new FileReader("src/main/resources/init_db.sql"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line);
                script.append("\n");
            }

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(script.toString())) {
                preparedStatement.execute();
                System.out.println("Database initialized successfully");
            }
        } catch (SQLException | IOException e) {
            throw new DataProcessingException("Failed to initialize DB for statement: "
                    + script.toString());
        }
    }
}

