package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "Jdbc:mysql://localhost:3306/books";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    private static final Properties properties;

    static {
        try {
            Class.forName( DRIVER_MYSQL );
        } catch ( ClassNotFoundException e ) {
            throw new RuntimeException( "Cannot get driver for DB", e );
        }

        properties = new Properties();
        properties.setProperty( "user", USER );
        properties.setProperty( "password", PASSWORD);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection( URL, properties );
    }
}
