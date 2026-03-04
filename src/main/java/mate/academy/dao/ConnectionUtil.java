package mate.academy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exceptions.DataProcessingException;

public class ConnectionUtil {
    private static final String DATABASE_URL =
            "jdbc:mysql://localhost:3306/book_db?serverTimezone=UTC";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", System.getenv("DB_PASSWORD"));
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't connect to DB", e);
        }
    }
}
