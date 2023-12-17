package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/test";
    private static final Properties CONNECTION_DATA = new Properties();

    static {
        CONNECTION_DATA.put("user", "root");
        CONNECTION_DATA.put("password", "yellowihor32");
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("JDBC driver loading failed", e);
        }

        return DriverManager.getConnection(CONNECTION_URL, CONNECTION_DATA);
    }
}
