package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load JDBC driver for MySQL", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "root");
        dbProperties.put("password", "00000");
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/books", dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection to DB", e);
        }
    }
}
