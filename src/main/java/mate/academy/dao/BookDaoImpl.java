package mate.academy.dao;

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
    private static final String CREATE_QUERY = "INSERT INTO books(title, price) VALUES (?, ?)";
    private static final String FIND_BY_ID = "SELECT * from books WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM books";
    private static final String UPDATE_QUERY = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                         Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException("Insert failed for book: " + book);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            book.setId(generatedKeys(generatedKeys));
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Book creation failed", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Book book = null;
            if (resultSet.next()) {
                book = parseResultSet(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Could not find the book by id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(FIND_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(parseResultSet(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not find books in DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException("Update failed for book with id:" + book.getId());
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not update the book", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not delete book by id: " + id, e);
        }
    }

    private Book parseResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }

    private Long generatedKeys(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new DataProcessingException("No generated key present");
        }
        return resultSet.getObject(1, Long.class);
    }
}
