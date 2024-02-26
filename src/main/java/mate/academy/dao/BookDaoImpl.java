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
    private static final String INSERT_QUERY = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM books WHERE id=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM books";
    private static final String UPDATE_QUERY = "UPDATE books SET title=?, price=? WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM books WHERE id=?";

    private static final String INSERT_ERROR_MESSAGE = "Insertion failed for a book: ";
    private static final String SELECT_BY_ID_ERROR_MESSAGE = "Failed to get a book by id: ";
    private static final String SELECT_ALL_ERROR_MESSAGE = "Failed to select all books";
    private static final String UPDATE_ERROR_MESSAGE = "Failed to update a book: ";
    private static final String DELETE_ERROR_MESSAGE = "Failed to to delete a book with id: ";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                     PreparedStatement statement = connection.prepareStatement(INSERT_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 0) {
                throw new RuntimeException(INSERT_ERROR_MESSAGE + book);
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(INSERT_ERROR_MESSAGE + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(SELECT_BY_ID_ERROR_MESSAGE + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = getFromResultSet(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(SELECT_ALL_ERROR_MESSAGE, e);
        }
        return books;
    }

    private Book getFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);

        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return book;
            } else {
                throw new DataProcessingException(UPDATE_ERROR_MESSAGE + book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(UPDATE_ERROR_MESSAGE + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(DELETE_ERROR_MESSAGE + id, e);
        }
    }
}
