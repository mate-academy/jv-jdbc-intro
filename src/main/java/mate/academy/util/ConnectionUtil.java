package mate.academy.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DbException;

public class ConnectionUtil {
    private static final Properties DB_PROPERTIES = new Properties();

    static {
        try {
            DB_PROPERTIES.load(new FileInputStream(Constants.APP_PROPERTIES_FILE));
            Class.forName(DB_PROPERTIES.getProperty(Constants.DB_DRIVER_TAG));
        } catch (IOException e) {
            throw new DbException("Can`t load JDBC driver for MYSQL: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new DbException("Can`t load driver properties: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    DB_PROPERTIES.getProperty(Constants.DB_URL_TAG));
        } catch (SQLException e) {
            throw new DbException("Can`t create connection for DB: " + e.getMessage());
        }
    }
}
