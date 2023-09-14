package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "1111");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library?serverTimezone=UTC", dbProperties);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load the JDBC driver", e);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a connection to the DB", e);
        }
    } 
}
