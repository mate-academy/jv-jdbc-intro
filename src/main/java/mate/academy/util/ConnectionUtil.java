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
            throw new RuntimeException("Can not load jdbc driver");
        }
    }

    public static Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.put("user", "USER_NAME");
            properties.put("password", "PASSWORD");
            return DriverManager.getConnection("jdbc:mysql://YOUR_DATABASE:PORT/jdbc_intro",
                    properties);
        } catch (SQLException e) {
            throw new RuntimeException("Can not get connection to DB");
        }
    }
}
