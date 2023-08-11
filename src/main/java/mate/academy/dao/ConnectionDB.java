package mate.academy.dao;

import mate.academy.exception.DataProcessingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class ConnectionDB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final Properties DB_PROPERTIES = new Properties();

    static {
        DB_PROPERTIES.put("user", "root");
        DB_PROPERTIES.put("password", "qwerty123456");
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't load driver ", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_PROPERTIES);
    }
}
