package mate.academy.until;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver.");
            Properties dbProps = new Properties();
            dbProps.put("user", "root");
            dbProps.put("password", "");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can not JDBC driver", e);
        } catch (SQLException e) {
            throw new RuntimeException("Can not create to connection",e);
        }
    }
}
