package mate.academy.dao;

import mate.academy.model.Qsetting;
import java.sql.*;

public class QsettingDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "---SQLdno12";

    public void save(Qsetting qsetting) {
        String sql = "INSERT INTO qsetting (name, ref, tune) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, qsetting.getName());
            stmt.setString(2, qsetting.getRef());
            stmt.setString(3, qsetting.getTune());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        qsetting = new Qsetting(generatedKeys.getInt(1), qsetting.getName(), qsetting.getRef(), qsetting.getTune());
                        System.out.println("Inserted: " + qsetting);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Qsetting getById(int id) {
        String sql = "SELECT id, name, ref, tune FROM qsetting WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Qsetting(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("ref"),
                            rs.getString("tune")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        QsettingDAO dao = new QsettingDAO();

        // Sample XML Data
        String xmlData = """
                <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                <tune> 
                    <string name="PRODUCT_CARD">JUNI</string>
                    <string name="BANK_NAME">PB</string>
                </tune>
                """;

        // Save data
        Qsetting qsetting = new Qsetting("Config1", "Ref1", xmlData);
        dao.save(qsetting);

        // Retrieve data
        Qsetting fetched = dao.getById(2);
        if (fetched != null) {
            System.out.println("Fetched from DB: " + fetched);
        }
    }
}
