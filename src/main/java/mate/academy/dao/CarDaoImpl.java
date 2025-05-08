package mate.academy.dao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Car;
import mate.academy.services.ConnectionUtil;


@Dao
public class CarDaoImpl implements CarDao {

    private static final String CREATE_QUERY = "INSERT INTO cars (brand, model, number, mileage, was_in_accident, price) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String FIND_ALL_QUERY = "SELECT * FROM cars";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM cars WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM cars WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE cars SET brand = ?, model = ?, number = ?, mileage = ?, price = ?, was_in_accident = ? WHERE id = ?";

    private static final String ID = "id";
    private static final String BRAND = "brand";
    private static final String MODEL = "model";
    private static final String NUMBER = "number";
    private static final String MILEAGE = "mileage";
    private static final String PRICE = "price";
    private static final String WAS_IN_ACCIDENT = "was_in_accident";
    private static final String ERROR = "Can not complete ";

    private static final int BRAND_INDEX = 1;
    private static final int MODEL_INDEX = 2;
    private static final int NUMBER_INDEX = 3;
    private static final int MILEAGE_INDEX = 4;
    private static final int PRICE_INDEX = 5;
    private static final int ACCIDENT_INDEX = 6;
    private static final int ID_INDEX_UPDATE = 7;
    private static final int ID_INDEX_COMMON = 1;
    private static final int MINIMAL_ROWS_TO_CHANGE = 1;

    @Override
    public Car create(Car car) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(BRAND_INDEX, car.getBrand());
            statement.setString(MODEL_INDEX, car.getModel());
            statement.setString(NUMBER_INDEX, car.getNumber());
            statement.setInt(MILEAGE_INDEX, car.getMileage());
            statement.setBigDecimal(PRICE_INDEX, car.getPrice());
            statement.setBoolean(ACCIDENT_INDEX, car.isInAccident());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < MINIMAL_ROWS_TO_CHANGE) {
                throw new DataProcessingException("Failed to create car " + car + " no rows were affected");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_INDEX_COMMON, Long.class);
                car.setId(id);
            }
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException(ERROR + " creation - " + car, e);
        }
    }

    @Override
    public List<Car> findAll() {
        List<Car> response = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                response.add(getCar(resultSet));
            }
            return response;
        } catch (SQLException e) {
            throw new DataProcessingException(ERROR + " finding all cars", e);
        }
    }

    @Override
    public Optional<Car> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(ID_INDEX_COMMON, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getCar(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(ERROR + " finding by id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Car update(Car car) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(BRAND_INDEX, car.getBrand());
            statement.setString(MODEL_INDEX, car.getModel());
            statement.setString(NUMBER_INDEX, car.getNumber());
            statement.setInt(MILEAGE_INDEX, car.getMileage());
            statement.setBigDecimal(PRICE_INDEX, car.getPrice());
            statement.setBoolean(ACCIDENT_INDEX, car.isInAccident());
            statement.setLong(ID_INDEX_UPDATE, car.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated < MINIMAL_ROWS_TO_CHANGE) {
                throw new DataProcessingException("Failed to update car, no rows were affected");
            }
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException(ERROR + " updating - " + car, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean delete(Car car) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(ID_INDEX_COMMON, car.getId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException(ERROR + " deleting - " + car, e);
        }
    }

    private Car getCar(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String brand = resultSet.getString("brand");
        String model = resultSet.getString("model");
        String number = resultSet.getString("number");
        int mileage = resultSet.getInt("mileage");
        boolean wasInAccident = resultSet.getBoolean("was_in_accident");
        BigDecimal price = resultSet.getBigDecimal("price");
        return new Car(id, brand, model, number, mileage, wasInAccident, price);
    }

}
