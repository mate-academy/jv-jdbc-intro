package mate.academy.util;

import mate.academy.exception.DataProcessingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/book_service?serverTimezone=UTC";
    private static final Properties DB_PROPERTIES = new Properties();;

    static {
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "Burunduk_2008");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load a JDBC driver to MySQL", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
