package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL =
            "jdbc:mysql://localhost:3306/book_store";

    private ConnectionUtil() {
    }

    public static Connection getConnection() throws SQLException {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "root");
        dbProperties.put("password", System.getenv("DB_PASSWORD"));

        return DriverManager.getConnection(URL, dbProperties);
    }
}
