package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    private static String DB_URL = "jdbc:mysql://localhost:3306/book_store";
    private static Properties DB_Properties;

    static {
        DB_Properties = new Properties();
        DB_Properties.put("user", "root");
        DB_Properties.put("password", "root1234");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Cannot load JDBC driver",e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_Properties);
    }
}
