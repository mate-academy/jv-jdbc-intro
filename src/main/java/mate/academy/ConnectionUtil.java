package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final Properties BOOKS_DB_PROPERTIES;

    static {
        BOOKS_DB_PROPERTIES = new Properties();
        BOOKS_DB_PROPERTIES.put("user", "root");
        BOOKS_DB_PROPERTIES.put("password", "");
    }

    private static final String MYSQL_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/library_db";

    public static Connection makeConnection() {
        try {
            Class.forName(MYSQL_CLASS_NAME);
            return DriverManager.getConnection(MYSQL_URL, BOOKS_DB_PROPERTIES);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
