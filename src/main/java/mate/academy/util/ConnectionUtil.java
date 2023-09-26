package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String DВ_URL = "jdbc:mysql://localhost:3306/books_schema";
    private static final Properties DB_PROPERTIES = new Properties();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load jdbc driver: ",e);
        }
        DB_PROPERTIES.put("user","root");
        DB_PROPERTIES.put("password","Haker2013");
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DВ_URL, DB_PROPERTIES);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t connect to DB with url"
                    + DВ_URL + "Properties: " + DB_PROPERTIES);
        }
    }
}
