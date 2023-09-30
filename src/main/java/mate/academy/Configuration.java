package mate.academy;

import java.util.Properties;
import mate.academy.util.PropertiesReaderUtil;

public class Configuration {
    private static final String PROPERTIES_FILE_NAME = "app.properties";
    private static Configuration config;
    private final Properties properties;
    private final String url;
    private final String userName;
    private final String password;
    private final boolean runDdlScript;

    private Configuration() {
        properties = PropertiesReaderUtil.getProperties(PROPERTIES_FILE_NAME);
        runDdlScript = Boolean.parseBoolean(properties.getProperty("run_script"));
        userName = properties.getProperty("user");
        password = properties.getProperty("password");
        url = properties.getProperty("url");
    }

    public static Configuration getInstance() {
        if (config == null) {
            config = new Configuration();
        }
        return config;
    }

    public Properties getProperties() {
        return properties;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRunDdlScript() {
        return runDdlScript;
    }
}
