package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String BD_URL = "jdbc:mysql://localhost:3306/jdbc_intro";
    private static final String BD_USERNAME = "root";
    private static final String BD_PASSWORD = "password";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t load JDBC driver!", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProps = new Properties();
        dbProps.put("user", BD_USERNAME);
        dbProps.put("password", BD_PASSWORD);

        try {
            return DriverManager.getConnection(BD_URL, dbProps);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create connection to DB!", e);
        }
    }
}
