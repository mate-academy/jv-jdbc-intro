package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "Jdbc:mysql://localhost:3306/books_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    private static final Properties PROPERTIES;

    static {
        try {
            Class.forName( DRIVER_MYSQL );
        } catch ( ClassNotFoundException e ) {
            throw new RuntimeException( "Cannot get driver for DB", e );
        }

        PROPERTIES = new Properties();
        PROPERTIES.setProperty( "user", USER );
        PROPERTIES.setProperty( "password", PASSWORD);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection( URL, PROPERTIES );
    }
}
