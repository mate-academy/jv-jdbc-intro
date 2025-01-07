package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test_dao_db"; //change database URL if needed
    private static final String DB_USER = "{USER NAME}"; // put database user name
    private static final String DB_PASSWORD = "{PASSWORD}"; // put database user password

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Can't get connection", e);
        }
    }
}
