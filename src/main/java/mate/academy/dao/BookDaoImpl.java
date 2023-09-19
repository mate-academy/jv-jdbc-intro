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
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String TITLE_COLUMN_NAME = "title";
    private static final String PRICE_COLUMN_NAME = "price";
    private static final String ID_COLUMN_NAME = "id";
    private static final String CREATE_QUERY = "INSERT INTO books (title, price) VALUES (?,?)";
    private static final String FINDBYID_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String FINDALL_QUERY = "SELECT * FROM books";
    private static final String UPDATE_QUERY = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(CREATE_QUERY,
                         Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert at least 1 row,"
                        + "but inserted 0 rows.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new book" + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(FINDBYID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(retrievingDataFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id=" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement
                         = connection.prepareStatement(FINDALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(retrievingDataFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books from DB", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to update at least one row,"
                        + "but updated 0 rows.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id" + id, e);
        }
    }

    private Book retrievingDataFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID_COLUMN_NAME);
        String title = resultSet.getString(TITLE_COLUMN_NAME);
        BigDecimal price = resultSet.getBigDecimal(PRICE_COLUMN_NAME);
        return new Book(id, title, price);
    }
}
