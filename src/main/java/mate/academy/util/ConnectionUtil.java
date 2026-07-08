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
            throw new RuntimeException("Can't find MySQL Driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user","root");
            dbProperties.put("password","movazot7a8");
            String url = "jdbc:mysql://localhost:3307/books?serverTimezone=UTC";
            return DriverManager.getConnection(url, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't establish connection",e);
        }

    }
}
