package mate.academy.dao;

import mate.academy.exceptions.DataProcessingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtilImpl implements ConnectionUtil {
    private static String db_url;
    private static final Properties DB_PROPERTIES;
    private static String password;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "illya");

        try {
            Class.forName("com.mysql.cj.jdbs.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not load JDBS driver", e);
        }
    }

    public static void setDbUrl(String db_url) {
        if (db_url == null) {
            throw new DataProcessingException("URL for database SQL is empty");
        }
        ConnectionUtilImpl.db_url = db_url;
    }

    public static void setPassword(String password) {
        if (password == null) {
            throw new DataProcessingException("Password for database SQL is empty");
        }
        ConnectionUtilImpl.password = password;
    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbs.mysql://localhost:3306/my_db", DB_PROPERTIES);
    }

}
