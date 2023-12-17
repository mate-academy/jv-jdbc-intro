package mate.academy.util;

import java.io.InputStream;

public class FileReaderUtil {

    private FileReaderUtil() {
    }

    public static InputStream read(String fileName) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        return contextClassLoader.getResourceAsStream(fileName);
    }
}
