package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.entity.Book;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.util.ConnectionManager;

@Dao
public class BookDaoImpl implements BookDao<Long, Book> {
    private static final String SQL_INSERT = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM books";
    private static final String SQL_UPDATE = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionManager.open();
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            validateAffectedRows(affectedRows);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book: " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionManager.open();
                PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Book book = null;
            if (resultSet.next()) {
                book = buildBook(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book with id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionManager.open();
                PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(buildBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't retrieve books", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionManager.open();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int updateCount = statement.executeUpdate();
            validateAffectedRows(updateCount);
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.open();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, id);
            int deleteCount = statement.executeUpdate();
            return deleteCount > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id, e);
        }
    }

    private void validateAffectedRows(int affectedRows) {
        if (affectedRows < 1) {
            throw new DataProcessingException(
                    "Expected to insert at least one row, but inserted 0 rows",
                    null);
        }
    }

    private Book buildBook(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getBigDecimal("price")
        );
    }
}
