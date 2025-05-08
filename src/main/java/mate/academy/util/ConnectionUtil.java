package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static final String SQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/books_db";
    public static final String USER = "root";
    public static final String PASSWORD = "!Vladius080197";

    static {
        try {
            Class.forName(SQL_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBC driver.", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.setProperty("user", USER);
            properties.setProperty("password", PASSWORD);
            return DriverManager.getConnection(DB_URL, properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can not create connection to database", e);
        }
    }
}
