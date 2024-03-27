package mate.academy;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties dbProperties = new Properties();
            dbProperties.put("user","root");
            dbProperties.put("password","12345678");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                        dbProperties);
            String sql = "SELECT * FROM car WHERE id = ?";
            PreparedStatement statment = connection.prepareStatement(sql);
            statment.setLong(1,1L);
            ResultSet resultSet = statment.executeQuery();
            if (resultSet.next()) {

                Long id = resultSet.getObject("id", Long.class);
                String model = resultSet.getString("model");
                Integer year = resultSet.getObject("year", Integer.class);
                System.out.println(id + model + year);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot load jdbc driver", e);
        } catch (SQLException e) {
            throw new RuntimeException("Connection to DB failed", e);
    }
    }
}
