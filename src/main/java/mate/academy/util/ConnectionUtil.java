package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE = "book_db";
    private static final String USER_NAME = "bestuser";
    private static final String DB_PASSWORD = "bestuser";

    {
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load jdbc driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", USER_NAME);
            dbProperties.put("password", DB_PASSWORD);
            return DriverManager.getConnection(DB_URL + DATABASE, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to " + DATABASE, e);
        }
    }
}
