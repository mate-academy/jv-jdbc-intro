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
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected paste at least 1 row, "
                        + "but was pasted 0 rows.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot save a book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = parseBook(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find book with id:" + id, e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        Book book;
        String sql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                book = parseBook(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find books in Database.", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(3, book.getId());
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected update at least 1 row, "
                        + "but was updated 0 rows.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to find book to update with id:"
                    + book.getId() + " in database.", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        int affectedRows;
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            affectedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete book with id:" + id, e);
        }
        return affectedRows > 0;
    }

    private Book parseBook(ResultSet resultSet) {
        Book book;
        try {
            long id = resultSet.getObject("id", Long.class);
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getObject("price", BigDecimal.class);
            book = new Book(id, title, price);
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot parse data to Book", e);
        }
        return book;
    }
}
