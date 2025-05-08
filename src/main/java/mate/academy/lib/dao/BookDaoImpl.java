package mate.academy.lib.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.exceptions.DataProcessingException;
import mate.academy.lib.model.Book;
import mate.academy.lib.settings.ConnectionUtil;
import mate.academy.lib.settings.Dao;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PRICE = "price";

    public Book create(Book book) {
        String query = "INSERT INTO books (title,price) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DataProcessingException("Creating book failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getObject(1, Long.class));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create a book " + book.getTitle(), e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SElECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(extractBookFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    books.add(extractBookFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get a list of books from DB.", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ? , price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DataProcessingException("Updating book failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not update the book with ID "
                    + book.getId(), e);
        }
        return findById(book.getId()).orElseThrow(() ->
                new DataProcessingException("The book with ID "
                        + book.getId() + " does not exist after update"));
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DataProcessingException("No book with ID "
                        + id + " was found to delete.");
            }
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete a book with ID " + id, e);
        }
    }

    private Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong(ID));
        book.setTitle(resultSet.getString(TITLE));
        book.setPrice(resultSet.getBigDecimal(PRICE));
        return book;
    }
}
