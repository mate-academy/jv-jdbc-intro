package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db_0";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "oDiN13oDiN13";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }
    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", USERNAME);
        dbProperties.put("password", PASSWORD);
        try {
            return DriverManager.getConnection(URL,  dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
