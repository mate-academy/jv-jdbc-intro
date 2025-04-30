package mate.academy.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/homework";
    private static final Properties DB_PROPERTIES;
    private static final String USER_DB = "root";
    private static final String PASSWORD_DB = "22M_$fJsha";

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USER_DB);
        DB_PROPERTIES.put("password", PASSWORD_DB);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connection to the database", e);
        }
    }
}
