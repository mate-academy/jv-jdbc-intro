package mate.academy;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.BookDao;
import mate.academy.lib.Dao;
import mate.academy.lib.DataProcessingException;

@Dao
public class BookDaoImpl implements BookDao {

    private static final String INSERT_SQL =
            "INSERT INTO Books (id, title, price) VALUES (?, ?, ?)";
    private static final String SELECT_BY_ID_SQL =
            "SELECT id, title, price FROM Books WHERE id = ?";
    private static final String SELECT_ALL_SQL =
            "SELECT id, title, price FROM Books";
    private static final String UPDATE_SQL =
            "UPDATE Books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_SQL =
            "DELETE FROM Books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, book.getId());
            statement.setString(2, book.getTitle());
            statement.setBigDecimal(3, book.getPrice());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException(
                        "Expected to insert at least one row, but inserted 0 rows");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getObject(1, Long.class);
                    book.setId(id);
                }
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new Book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(createBookFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Error while finding Book by id: " + id, e);
        }

        return Optional.empty();
    }

    private Book createBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        return new Book(id, title, price);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL)) {

            while (resultSet.next()) {
                books.add(createBookFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Error while finding all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book updatedBook) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {

            statement.setString(1, updatedBook.getTitle());
            statement.setBigDecimal(2, updatedBook.getPrice());
            statement.setLong(3, updatedBook.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException("No book found with id: " + updatedBook.getId());
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Error while updating Book: " + updatedBook, e);
        }
        return updatedBook;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {

            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new DataProcessingException("Error while deleting Book by id: " + id, e);
        }
    }
}
