package mate.academy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import mate.academy.exception.DataProcessingException;

public class DatabaseInitializer {
    private static final String FILE_SQL = "src/main/resources/init_db.sql";

    public static void initialize() {
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {

            String sql = readSqlScript(FILE_SQL);
            statement.execute(sql);
            System.out.println("Базу даних ініціалізовано успішно.");
        } catch (Exception e) {
            throw new DataProcessingException("Не вдалося ініціалізувати базу даних", e);
        }
    }

    private static String readSqlScript(String fileName) {
        try {
            return String.join("\n", Files.readAllLines(Path.of(fileName)));
        } catch (IOException e) {
            throw new RuntimeException("Can not read from the file: " + fileName, e);
        }
    }
}
