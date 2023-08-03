package mate.academy;

import mate.academy.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectionUtil.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute("INSERT INTO books (title, price) VALUES ('dsa', 77)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
