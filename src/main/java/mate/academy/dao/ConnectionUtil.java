package mate.academy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import mate.academy.DataProcessingException;

public class ConnectionUtil {
    private static final String JDBC_URL =
            "jdbc:mysql://localhost:3306/books_db?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "64850078Liza";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not connect to database", e);
        }
    }
}
