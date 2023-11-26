package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/jdbc-intro";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "12091972";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connected to DB: " + JDBC_URL, e);
        }
    }
}
