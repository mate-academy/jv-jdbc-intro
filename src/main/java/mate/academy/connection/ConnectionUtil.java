package mate.academy.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL_DB = "jdbc:mysql://localhost:3306/test";
    private static final Properties PROPERTIES_DB;

    static {
        PROPERTIES_DB = new Properties();
        PROPERTIES_DB.put("user", "root");
        PROPERTIES_DB.put("password", "120986Dima!");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find name " + e);
        }

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_DB, PROPERTIES_DB);
    }

}
