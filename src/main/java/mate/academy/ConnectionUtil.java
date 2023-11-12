package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bookDB";
    private static final Properties DB_PROPERTIES;
    private static final String NOT_LOAD_DRIVE = "Can't loan JDBC driver";
    private static final String DRIVE = "com.mysql.cj.jdbc.Driver";

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "ZXCqwe123");
        try {
            Class.forName(DRIVE);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException(NOT_LOAD_DRIVE, e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
