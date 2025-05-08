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

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        final String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            final int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert at least one row, but "
                        + "inserted 0 rows.", new RuntimeException());
            }
            final ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                final Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book: " + book, e.getCause());
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        final String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(convertRowToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id=" + id, e.getCause());
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        final String query = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(convertRowToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get list of books.", e.getCause());
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        final String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(3, book.getId());
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            final int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to update at least one row, but "
                        + "updated 0 rows.", new RuntimeException());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book with id=" + book.getId(),
                    e.getCause());
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        final String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            final int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id=" + id, e.getCause());
        }
    }

    private Book convertRowToBook(final ResultSet resultSet) throws SQLException {
        final Long fetchedId = resultSet.getObject("id", Long.class);
        final String title = resultSet.getString("title");
        final BigDecimal price = resultSet.getBigDecimal("price");
        Book book = new Book();
        book.setId(fetchedId);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
