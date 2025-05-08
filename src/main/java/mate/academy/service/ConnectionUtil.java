package mate.academy.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_USER_NAME = "root";
    private static final String DB_PASSWORD = "getdataSQLjul";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shop";
    private static final String MYSQL_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final Properties DB_Properties;

    static {
        DB_Properties = new Properties();
        DB_Properties.put("user", DB_USER_NAME);
        DB_Properties.put("password", DB_PASSWORD);
        try {
            Class.forName(MYSQL_CLASS);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load MySQL JDBC driver class", e);
        }
    }

    public static Connection create() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_Properties);
    }
}
