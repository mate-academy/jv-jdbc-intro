package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class DatabaseInitializer {
    private static final String SQL_FILE_PATH = "/init_db.sql";
    private static final String QUERY_DELIMITER = ";";

    public static void initialize() {
        if (DatabaseInitializer.class.getResourceAsStream(SQL_FILE_PATH) == null) {
            throw new RuntimeException(String.format("File %s was not found: ", SQL_FILE_PATH));
        }

        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement();
                BufferedReader reader = new BufferedReader(
                         new InputStreamReader(Objects.requireNonNull(
                             DatabaseInitializer.class.getResourceAsStream(SQL_FILE_PATH))))) {

            StringBuilder sqlQuery = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlQuery.append(line).append("\n");
            }

            String[] queries = sqlQuery.toString().split(QUERY_DELIMITER);
            for (String query : queries) {
                if (!query.trim().isEmpty()) {
                    try {
                        statement.executeUpdate(query);
                    } catch (SQLException e) {
                        throw new RuntimeException(
                                "SQL execution failed for query: " + query, e);
                    }
                }
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed initializing database: ", e);
        }
    }
}
