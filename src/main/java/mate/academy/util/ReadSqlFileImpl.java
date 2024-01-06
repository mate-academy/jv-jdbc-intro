package mate.academy.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadSqlFileImpl implements ReadSqlFile {
    public static final String PATH = "src/main/java/resources/init_db.sql";

    @Override
    public String getQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(PATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("can't read file:" + PATH, e);
        }
        return stringBuilder.toString();
    }
}
