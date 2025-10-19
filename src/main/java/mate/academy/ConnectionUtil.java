package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public final class ConnectionUtil {
    private static final String URL =
            "jdbc:mysql://localhost:3306/bookstore_db?serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASSWORD = "lonylony0045!";

    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USER);
        DB_PROPERTIES.put("password", PASSWORD);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver", e);
        }
    }

    private ConnectionUtil() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a database connection", e);
        }
    }
}
