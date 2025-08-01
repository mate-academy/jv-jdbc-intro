package mate.academy.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "Qwerty123!";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Can't establish DB connection", e);
        }
    }
}

