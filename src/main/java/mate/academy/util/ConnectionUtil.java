package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException error1) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", error1);
        }
    }

    public static Connection getConnection() {
        try {

            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "111111qQ!");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/test", dbProperties);
        } catch (SQLException error2) {
            throw new RuntimeException("Can't create connection to DataBase", error2);
        }
    }
}
