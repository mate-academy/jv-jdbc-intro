package mate.academy.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ReadSqlScriptDaoImp implements ReadSqlScriptDao {

    @Override
    public String readSqlScript(String fileName) {
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new RuntimeException("File not found: " + fileName);
            }
            return new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Error reading SQL script: " + fileName, e);
        }
    }
}
