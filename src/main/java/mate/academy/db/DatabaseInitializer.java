package mate.academy.db;

import java.io.FileWriter;
import java.io.IOException;

public class DatabaseInitializer {
    private static final String FILE_PATH = "src/main/resources/init_db.sql";
    private static final String SQL_SCRIPT = """
        CREATE TABLE `books` (
            `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
            `title` VARCHAR(255) NOT NULL,
            `price` INT NOT NULL
        );
            """;

    public static void initializeDatabaseScript() {
        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            fileWriter.write(SQL_SCRIPT);
            System.out.println("Файл init_db.sql успішно створено та записано.");
        } catch (IOException e) {
            System.out.println("Помилка при створенні init_db.sql: " + e.getMessage());
        }
    }
}
