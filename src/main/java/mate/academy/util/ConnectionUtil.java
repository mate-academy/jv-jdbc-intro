package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bookshop";
    private static final Properties DB_PROPERTIES;
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Wetuop34!";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_KEY = "user";
    private static final String PASSWORD_KEY = "password";

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put(USER_KEY, DB_USER);
        DB_PROPERTIES.put(PASSWORD_KEY, DB_PASSWORD);

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load MSQL driver" + e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
