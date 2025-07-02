package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/book_store?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Zheck@234536";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
