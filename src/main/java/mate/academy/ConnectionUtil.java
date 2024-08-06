package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    public static Connection getConnection() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bookstore?serverTimezone=UTC";
        String username = "root";
        String password = "1234";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
