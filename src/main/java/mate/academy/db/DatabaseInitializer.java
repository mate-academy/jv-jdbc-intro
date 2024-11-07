package mate.academy.db;

import java.io.FileWriter;
import java.io.IOException;

public class DatabaseInitializer {
    private static final String FILE_PATH = "src/main/resources/init_db.sql";
    private static final String SQL_SCRIPT = """
        CREATE TABLE `books` (
            `id` INT NOT NULL AUTO_INCREMENT,
            `title` VARCHAR(255) NOT NULL,
            `price` DECIMAL(10, 2) NOT NULL
            PRIMARY KEY(`id`)
        );
        """;

    public static void initializeDatabaseScript() {
        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            fileWriter.write(SQL_SCRIPT);
            System.out.println("The file init_db.sql successfully created and written.");
        } catch (IOException e) {
            System.out.println("Can not created the file init_db.sql: " + e.getMessage());
        }
    }
}
