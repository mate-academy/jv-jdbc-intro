package mate.academy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import mate.academy.model.DataProcessingException;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
    }
}
