package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL =
            "jdbc:mysql://localhost:3306/book_store?serverTimezone=UTC";

    private static final Properties DB_PROPERTIES = new Properties();

    static {
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "Maksym$31902991");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_PROPERTIES);
    }
}
