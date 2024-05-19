package mate.academy.util;

import mate.academy.exception.DataProcessingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/book_schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't connect to database", e);
        }

        return connection;
    }
}
