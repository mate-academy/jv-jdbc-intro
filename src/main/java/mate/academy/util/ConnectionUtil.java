package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

    private static final String DATABASE_URL =
            "jdbc:mysql://localhost:3306/book_store?serverTimezone=UTC";

    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        PROPERTIES.put("user", "root");
        PROPERTIES.put("password", "root1234");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cant load JDBC driver", e);
        }

    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL, PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get connection with database", e);
        }
    }
}
