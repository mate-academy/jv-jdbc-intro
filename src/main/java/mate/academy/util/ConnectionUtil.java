package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String PASSWORD = "anton7570857";
    private static final String JDBC_URL_CONNECTION = "jdbc:mysql://localhost:3306/test";

   static {
       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
       } catch (ClassNotFoundException e) {
           throw new RuntimeException("Can not load JDBC driver", e);
       }
   }

    public static Connection getConnection() {
        try {
            Properties dbProperties = new Properties();
            dbProperties.put("user", "root");
            dbProperties.put("password", PASSWORD);
            return DriverManager.getConnection(JDBC_URL_CONNECTION, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
