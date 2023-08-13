package mate.academy.util;

import mate.academy.lib.DataProcessingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Cant load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "admin");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db",
                    dbProperties);
        } catch (SQLException x) {
            throw new DataProcessingException("Cant create connection to DB", x);
        }
    }
}
