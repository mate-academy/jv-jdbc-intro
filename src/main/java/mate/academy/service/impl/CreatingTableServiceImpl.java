package mate.academy.service.impl;

import static java.lang.System.lineSeparator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.service.CreatingTableService;

public class CreatingTableServiceImpl implements CreatingTableService {
    private static final String FILE_PATH = "src/main/resources/init_db.sql";
    private StringBuilder sqlScript = new StringBuilder();

    @Override
    public void createTable() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                sqlScript.append(line);
                sqlScript.append(lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + FILE_PATH);
        }
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            String[] commands = sqlScript.toString().split(";");
            for (String command : commands) {
                if (!command.trim().isEmpty()) {
                    statement.execute(command);
                }
            }
            System.out.println("Table created successfully!");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a table in a DB", e);
        }
    }
}
