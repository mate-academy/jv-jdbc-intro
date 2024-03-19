package mate.academy.lib;

import mate.academy.ConnectionUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseInitializer {
    public static void initializeDatabase() {
        try (Connection connection = ConnectionUtil.getConnection();
             BufferedReader reader = new BufferedReader(
                     new FileReader("src/main/resources/init_db.sql"))) {
            StringBuilder script = new StringBuilder();
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
            e.printStackTrace();
        }
    }
}

