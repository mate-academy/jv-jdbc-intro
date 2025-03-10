package mate.academy.dao;

import mate.academy.Exception.DataProcessingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionToDB {
    private static final String URL_DB = "jdbc:mysql://localhost:3306/matedb";
    private static final String LOGIN = "root";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String REAL_PASSWORD = "1357906_yariK";

    public static Connection connectToDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties properties = new Properties();
            properties.put(USER, LOGIN);
            properties.put(PASSWORD, REAL_PASSWORD);
            return DriverManager.getConnection(URL_DB, properties);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DataProcessingException("Can not make a connection to BD", e);
        }
    }
}
