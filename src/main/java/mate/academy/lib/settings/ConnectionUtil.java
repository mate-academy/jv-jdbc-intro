package mate.academy.lib.settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL_DB = "jdbc:mysql://localhost:3306/hw_book";
    private static final Properties DB_PROPERTIES = new Properties();

    static {
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "pass");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(URL_DB, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}
