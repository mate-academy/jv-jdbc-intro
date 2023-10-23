package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL_DB = "jdbc:mysql://localhost:3306/mateDB";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user","root");
        DB_PROPERTIES.put("password","Qwerfdsa!");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC Driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_DB,DB_PROPERTIES);
    }
}
