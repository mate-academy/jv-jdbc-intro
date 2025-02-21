package mate.academy.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DbConnector {
    public static final String ERROR_DURING_CONNECTION_CREATION =
            "Error during connection creation";
    private final Properties properties;

    public DbConnector() {
        properties = new Properties();
        try {
            FileReader fr = new FileReader("src/main/resources/security.properties");
            properties.load(fr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/mate", properties);
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_DURING_CONNECTION_CREATION, e);
        }
    }
}
