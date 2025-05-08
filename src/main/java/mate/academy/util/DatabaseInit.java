package mate.academy.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;

public class DatabaseInit {
    public static void executeSqlScript(String scriptPath, Connection connection) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                DatabaseInit.class.getClassLoader().getResourceAsStream(scriptPath))
                )
        )) {
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line);
                if (line.trim().endsWith(";")) {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(sqlBuilder.toString());
                        sqlBuilder.setLength(0);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while executing script: " + scriptPath, e);
        }
    }
}
