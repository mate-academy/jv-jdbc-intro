package mate.academy.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import mate.academy.service.Reader;

public class ReaderImpl implements Reader {
    @Override
    public String readFromFile(String filename) {
        try {
            return Files.readString(Path.of(filename));
        } catch (IOException e) {
            throw new RuntimeException("Can`t read from file",e);
        }
    }
}
