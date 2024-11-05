package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static Connection getConnection() {
        try {
            String className = "com.mysql.cj.jdbc.Driver";
            Class.forName(className);
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "HQaHPS");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/bookDB", dbProperties);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load JDBC driver", e);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create a connection to the DB", e);
        }
    }
}
