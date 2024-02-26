package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "root");
        dbProperties.put("password", "3052821794");
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to db ", e);
        }
    }
}
