package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost/db"
            + "?user=root&password=cbhtytdtymrbq70";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load this JDBC driver ",e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to database", e);
        }
    }
}
