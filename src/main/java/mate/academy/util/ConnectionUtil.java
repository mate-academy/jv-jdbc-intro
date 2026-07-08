package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/test";

    private static Properties DataBaseProperties = null;

    static {
        DataBaseProperties = new Properties();
        DataBaseProperties.put("user", "root");
        DataBaseProperties.put("password", "kali");

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DataBaseProperties);
    }
}
