package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/books_db";
    private static final Properties DB_PRORETIES;

    static {
        DB_PRORETIES = new Properties();
        DB_PRORETIES.put("user", "root");
        DB_PRORETIES.put("password", "V11M07k2001mm");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConnection() throws SQLException {
       return DriverManager.getConnection(DB_URL, DB_PRORETIES);
    }
}
