package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jv_jdbc_intro";
    private static final Properties BD_Properties;

    static {
        BD_Properties = new Properties();
        BD_Properties.put("user", "root");
        BD_Properties.put("password", "root123!@#");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cant upload MySQL driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, BD_Properties);
    }
}
