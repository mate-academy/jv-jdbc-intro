package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/test_dao_db";
    public static final String DB_USER = ""; // put database user name
    public static final String DB_PASSWORD = ""; // put database user password

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get connection", e);
        }
    }
}
