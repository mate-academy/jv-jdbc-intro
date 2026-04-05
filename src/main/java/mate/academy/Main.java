package mate.academy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties dbProporties = new Properties();
            dbProporties.put("user", "root");
            dbProporties.put("password", "12345");
            Connection connection =
                    DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", dbProporties);
            String sql = "SELECT * FROM car WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, 1L);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load MSQL file", e);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection", e);
        }
    }
}
