package mate.academy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class PropertiesUtil {
    private static final String PROPERTIES_FILE = "application.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public PropertiesUtil() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new DataProcessingException("Can't get data from: " + PROPERTIES_FILE, e);
        }
    }
}
