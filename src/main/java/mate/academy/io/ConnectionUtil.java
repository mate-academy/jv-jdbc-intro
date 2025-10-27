package mate.academy.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

    private static final String URL = "jdbc:mysql://localhost:3310/mate_academy?serverTimezone=UTC";
    private static final Properties INFO = new Properties();

    static {
        INFO.put("user", "ma");
        INFO.put("password", "FaWEtIgE");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, INFO);
    }
}
