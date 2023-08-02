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
            throw new RuntimeException("Can't get Driver", e);
        }
    }
    public static Connection getConnection() {
        Connection connection;
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user" , "root");
            dbProperties.put("password", "JDBC1901MySQL");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
