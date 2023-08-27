package mate.academy.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tasks",
                    "serg",
                    "22062020");
            return connection;
        } catch (ClassNotFoundException e) {
            throw new DataProcessingException("Can't find driver!", e);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't connect to DB!", e);
        }
    }
}
