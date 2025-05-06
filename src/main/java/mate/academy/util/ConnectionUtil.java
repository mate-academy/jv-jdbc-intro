package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/book_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1984";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.setProperty("user", USERNAME);
        DB_PROPERTIES.setProperty("password", PASSWORD);

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find SQL Driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to Db: book_db", e);
        }
    }
}
