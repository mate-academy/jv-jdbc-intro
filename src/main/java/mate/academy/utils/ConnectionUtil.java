package mate.academy.utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final Properties DB_PROPERTIES;

    static {
        Dotenv dotenv = Dotenv.load();

        DB_PROPERTIES = new Properties();

        DB_PROPERTIES.put("user", dotenv.get("DB_USER"));
        DB_PROPERTIES.put("password", dotenv.get("DB_PASSWORD"));

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
