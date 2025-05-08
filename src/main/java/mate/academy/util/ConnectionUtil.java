package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbc_intro";
    private static final Properties DB_PROPS;

    static {
        DB_PROPS = new Properties();
        DB_PROPS.put("user", "mate");
        DB_PROPS.put("password", "mate");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPS);
    }
}
