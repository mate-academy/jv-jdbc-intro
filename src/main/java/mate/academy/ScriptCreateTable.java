package mate.academy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ScriptCreateTable {
    public String loadSqlScriptFromFile(File scriptFile) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
            String value = reader.readLine();
            while (value != null) {
                stringBuilder.append(value).append(System.lineSeparator());
                value = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading SQL script file: "
                    + scriptFile.getAbsolutePath(), e);
        }
        return stringBuilder.toString();
    }

    public void executeCreateTableQuery(String sqlCreateTable) {
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCreateTable);
        } catch (SQLException e) {
            throw new RuntimeException("Error executing CREATE TABLE query: " + sqlCreateTable, e);
        }
    }
}
