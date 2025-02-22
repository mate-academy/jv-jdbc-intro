package mate.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.models.Manufacturer;
import mate.jdbc.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers(name, country) values(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturerStatement =
                        connection.prepareStatement(query,
                                Statement.RETURN_GENERATED_KEYS)) {
            manufacturerStatement.setString(1, manufacturer.getName());
            manufacturerStatement.setString(2, manufacturer.getCountry());
            manufacturerStatement.executeUpdate();
            ResultSet generatedKey = manufacturerStatement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                manufacturer.setId(id);
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert format from DB", e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = false AND id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturerStatement = connection.prepareStatement(query)) {
            manufacturerStatement.setLong(1, id);
            ResultSet resultSet = manufacturerStatement
                    .executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = getManufacturer(resultSet);
            }
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer by id from DB", e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE is_deleted = false";
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement manufacturerStatement = connection.createStatement()) {
            ResultSet resultSet = manufacturerStatement
                    .executeQuery(query);
            while (resultSet.next()) {
                allManufacturers.add(getManufacturer(resultSet));
            }
            return allManufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers from DB", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, country = ? "
                + "WHERE is_deleted = false AND id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturerStatement =
                        connection.prepareStatement(query)) {
            manufacturerStatement.setString(1, manufacturer.getName());
            manufacturerStatement.setString(2, manufacturer.getCountry());
            manufacturerStatement.setLong(3, manufacturer.getId());
            manufacturerStatement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer from DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET is_deleted = true WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement manufacturerStatement =
                        connection.prepareStatement(query)) {
            manufacturerStatement.setLong(1, id);
            return manufacturerStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer from DB", e);
        }
    }

    private Manufacturer getManufacturer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Manufacturer manufacturerFormat = new Manufacturer();
        manufacturerFormat.setId(id);
        manufacturerFormat.setName(name);
        manufacturerFormat.setCountry(country);
        return manufacturerFormat;
    }
}
