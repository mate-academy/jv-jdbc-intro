package mate.academy.dao.impl;

import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String request = "INSERT INTO books (title, price) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     request, PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(
                        "Expected to insert at leas one row, but inserted 0 rows.");
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't save a book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String request = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(initializedBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id= " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String request = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(initializedBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books from DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        String request = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            statement.setObject(3, book.getId());
            if (statement.executeUpdate() < 1) {
                throw new RuntimeException(
                        "Expected to insert at least one row, but inserted 0 rows");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String request = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setObject(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id= " + id, e);
        }
    }
    private Book initializedBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = BigDecimal.valueOf(resultSet.getLong("price"));
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
