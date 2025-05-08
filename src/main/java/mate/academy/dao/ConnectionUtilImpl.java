package mate.academy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtilImpl implements ConnectionUtil {
    private static final String USER = "root";
    private static final String PASSWORD = "illya";
    private static final String DB_URL = "jdbs.mysql://localhost:3306/my_db";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", USER);
        DB_PROPERTIES.put("password", PASSWORD);

        try {
            Class.forName("com.mysql.cj.jdbs.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBS driver", e);
        }
    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }

}
