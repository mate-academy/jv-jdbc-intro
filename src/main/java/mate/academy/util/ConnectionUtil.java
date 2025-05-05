package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "1234";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/home_work1";
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(CONNECTION_URL, USER_NAME, USER_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to db", e);
        }
    }
}
