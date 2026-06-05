package mate.academy.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    ConfigLoader.getUrl(),
                    ConfigLoader.getUser(),
                    ConfigLoader.getPassword()
            );

        } catch (SQLException e) {
            throw new RuntimeException("Can't create DB connection", e);
        }
    }
}
