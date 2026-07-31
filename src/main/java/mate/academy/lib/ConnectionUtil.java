package mate.academy.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/library_db?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    private ConnectionUtil() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }
}
