package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/jdbc_intro_trokhymchuk";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "12091972");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("We can`t load jdbc driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connected to the DB: " + JDBC_URL, e);
        }
    }
}
