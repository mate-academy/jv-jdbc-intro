package mate.academy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReaderUtil {
    private static Properties properties;

    private PropertiesReaderUtil() {
    }

    public static Properties getProperties(String filename) {
        if (properties == null) {
            try {
                InputStream inputStream = FileReaderUtil.read(filename);
                properties = new Properties();
                properties.load(inputStream);
            } catch (IOException e) {
                throw new RuntimeException("Can't load properties file " + filename, e);
            }
        }
        return properties;
    }
}
