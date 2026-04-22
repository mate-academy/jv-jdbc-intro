package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static Connection getConnection() {

        String className = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(className);
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", "root");

            return DriverManager.getConnection("jdbc:mysql://localhost:3306/test", dbProperties);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
