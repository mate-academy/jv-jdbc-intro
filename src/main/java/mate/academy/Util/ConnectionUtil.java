package mate.academy.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/book_schema";
    public static final Properties DB_PROPERTIES;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "Qwerty");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver: " + DRIVER, e);
        }
    }

    public static Connection getconnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }

}
