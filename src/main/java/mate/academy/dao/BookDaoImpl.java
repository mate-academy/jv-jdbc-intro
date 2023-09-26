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
import mate.academy.utils.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String DELETE_BOOK_QUERY = "DELETE FROM books WHERE id=?";
    private static final String CREATE_BOOK_QUERY = "INSERT INTO books (title, price) VALUES (?,?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM books WHERE id=?";
    private static final String GET_ALL_BOOKS_QUERY = "SELECT * FROM books";
    private static final String UPDATE_BOOK_QUERY = "UPDATE books SET title=?, price=? WHERE id=?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_BOOK_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 1) {
                throw new DataProcessingException("No rows were affected during book insertion");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to insert new book", e);
        }
    }
    
    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapToBook(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format("Failed to find a book by id - %s", id), e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_ALL_BOOKS_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(mapToBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Try to find all books was unsuccessful: ", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK_QUERY)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            if (statement.executeUpdate() <= 0) {
                throw new DataProcessingException("No rows were affected during book update");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    String.format("Failed to update a book with id - %s", book.getId()), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BOOK_QUERY)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Try to delete a book with id - %d was unsuccessful: ", e);
        }
    }

    private Book mapToBook(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getBigDecimal("price")
        );
    }
}
