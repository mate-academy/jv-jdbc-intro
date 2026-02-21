package mate.academy.lib;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * простий ConnectionUtil з DriverManager.getConnection(url, user, pass).
 * Не забувай закривати ресурси в finally або використовуй try-with-resources.
 */
public class ConnectionUtil {
    String url;
    String user;
    String pass;

    {
        try {
            DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
