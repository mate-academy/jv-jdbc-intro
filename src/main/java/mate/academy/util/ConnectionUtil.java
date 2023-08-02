package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    public static final String URL = "jdbc:mysql://localhost:3306/library";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "olegAndriy";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load JDBC driver for MySQL", e);
        }
    }

        public static Connection getConnection() {
            Properties dbProperties = new Properties();
            dbProperties.setProperty("user", USERNAME);
            dbProperties.setProperty("password", PASSWORD);
            try {
                return DriverManager.getConnection(URL, dbProperties);
            } catch (SQLException e) {
                throw new RuntimeException("Can't create connection to DataBase", e);
            }
        }
    }
