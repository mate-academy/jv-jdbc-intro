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
            throw new RuntimeException("Can`t load JDBC Driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "13289812");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/ticket_app",
                    dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("can`t create connection to Database", e);
        }
    }
}



