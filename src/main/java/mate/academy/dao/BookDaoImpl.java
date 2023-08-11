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
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

/**
 * You should rethrow DataProcessingException in catch block on dao layer
 * If you can't connect to your db because of this error:
 * The server time zone value ‘EEST’ is unrecognized or represents more than one time zone.
 * Try to set timezone explicitly in your connection URL.
 * Example:
 * ...localhost:3306/your_schema?serverTimezone=UTC
 * Or you can set a timezone in MySql directly by running command: SET GLOBAL time_zone = '+3:00';
 * throw new DataProcessingException("Can't get a book by id " + id, e);
 *     throw new DataProcessingException("Can't save a book " + book, e);
 */
@Dao
public class BookDaoImpl implements BookDao {
    private static final int ID_COLUMN_INDEX = 1;
    private static final int TITLE_COLUMN_INDEX = 2;
    private static final int PRICE_COLUMN_INDEX = 3;
    private static final int FIRST_PARAMETER = 1;
    private static final int SECOND_PARAMETER = 2;
    private static final int THIRD_PARAMETER = 3;
    private static final String TITLE_COLUMN_LABEL = "title";
    private static final String PRICE_COLUMN_LABEL = "price";

    @Override
    public void create(Book book) {
        String insertQuery = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(FIRST_PARAMETER, book.getTitle());
            preparedStatement.setBigDecimal(SECOND_PARAMETER, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("No inserted data! Something wrong with book: " + book);
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(ID_COLUMN_INDEX));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error during inserting book: " + book, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String selectAllQuery = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.prepareStatement(selectAllQuery)) {
            ResultSet resultSet = statement.executeQuery(selectAllQuery);
            List<Book> acquiredBooks = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong(ID_COLUMN_INDEX);
                String title = resultSet.getString(TITLE_COLUMN_INDEX);
                BigDecimal price = resultSet.getObject(PRICE_COLUMN_INDEX, BigDecimal.class);
                Book nextBook = new Book(id, title, price);
                acquiredBooks.add(nextBook);
            }
            return acquiredBooks;
        } catch (SQLException e) {
            throw new DataProcessingException("Error during finding all books", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteRowByIdQuery = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(deleteRowByIdQuery)) {
            preparedStatement.setLong(FIRST_PARAMETER,id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Error deleting book by id: " + id, e);
        }
    }

    @Override
    public Book update(Book book) {
        String updateQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(FIRST_PARAMETER, book.getTitle());
            preparedStatement.setBigDecimal(SECOND_PARAMETER, book.getPrice());
            preparedStatement.setLong(THIRD_PARAMETER, book.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("No updated data! Data should to update:"
                        + book);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Error during updating books database with book: "
                    + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String deleteRowByIdQuery = "SELECT * FROM books WHERE id = ?";
        Optional<Book> value = Optional.empty();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(deleteRowByIdQuery)) {
            preparedStatement.setLong(FIRST_PARAMETER,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString(TITLE_COLUMN_LABEL);
                BigDecimal price = resultSet.getObject(PRICE_COLUMN_LABEL,
                        BigDecimal.class);
                Book aquiredBook = new Book(id, title, price);
                value = Optional.of(aquiredBook);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error during finding book by id: " + id, e);
        }
        return value;
    }
}
