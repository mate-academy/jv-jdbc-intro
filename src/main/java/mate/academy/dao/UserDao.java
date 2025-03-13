package mate.academy.dao;

import mate.academy.model.User;
import java.sql.*;

public class UserDao {
    private static final String URL = "jdbc:postgresql://localhost:5432/juvalugma";
    private static final String USER = "postgres";
    private static final String PASSWORD = "12345";

    public void save(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?) ON CONFLICT (email) DO NOTHING";
//

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user = new User(generatedKeys.getInt(1), user.getName(), user.getEmail(), user.getPassword());
                        System.out.println("Inserted: " + user);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public User getById(int id) {
        String sql = "SELECT id, name, email, password FROM users WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        UserDao dao = new UserDao();


        User user = new User("John De", "john.doe@etjz.com", "secure password");
        dao.save(user);

        User fetched = dao.getById(1);
        if (fetched != null) {
            System.out.println("Fetched from : " + fetched);
        }
    }
}
