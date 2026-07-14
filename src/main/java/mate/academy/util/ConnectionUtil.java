package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/mysql?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Acid-33134";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find MySQL Driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't establish connection to DB", e);
        }
    }
}
