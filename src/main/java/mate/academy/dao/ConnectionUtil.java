package mate.academy.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/users";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "cotbyc-suncem-9Jyxby";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load MySQL Driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Can't connect to database", e);
        }
    }
}

