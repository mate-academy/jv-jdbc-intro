package mate.academy.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbc-intro";
    private static final Properties DB_PROPERTIES;

    static {
        DB_PROPERTIES = new Properties();
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "6855");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (DataProcessingException | ClassNotFoundException exception) {
            throw new DataProcessingException("There is no way to load the JDBC driver, "
                    + exception);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
        } catch (DataProcessingException | SQLException exception) {
            throw new DataProcessingException("There is no way to connect to the database, "
                    + exception);
        }
    }
}
