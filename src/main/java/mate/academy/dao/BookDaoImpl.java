package mate.academy.dao;

import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao{
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             statement.setString(1, book.getTitle());
             statement.setBigDecimal(2, book.getPrice());
            // Check if at least one row was changed in the database.
             if (statement.executeUpdate() < 1) {
                 // If no changes were made, throw a RuntimeException
                 // with a custom message indicating that at least one row was expected to be inserted, but 0 rows were inserted.
                 throw new RuntimeException("Expected to insert at least one row, but inserted 0 rows.");
             }
             ResultSet generatedKeys = statement.getGeneratedKeys();
             if (generatedKeys.next()) {
                 Long id = generatedKeys.getObject(1, Long.class);
                 book.setId(id);
             }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
             statement.setLong(1, id);
             ResultSet resultSet = statement.executeQuery();
             if (resultSet.next()) {
                 return Optional.of(getBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t find book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
             ResultSet resultSet = statement.executeQuery();
             while (resultSet.next()) {
                 bookList.add(getBook(resultSet));
             }
             return bookList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get boooks from db", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
             statement.setString(1, book.getTitle());
             statement.setBigDecimal(2, book.getPrice());
             statement.setLong(3, book.getId());
            if (statement.executeUpdate() < 1) {
                throw new RuntimeException("Expected to update at least one row, but update 0 rows.");
            }
        } catch (SQLException e) {
            // If no rows were updated, throw a RuntimeException
            // with a custom message indicating the expectation to update at least one row, but 0 rows were updated.
            throw new DataProcessingException("Can`t update book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int updatedRows = statement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete id: " + id, e);
        }
    }

    private Book getBook(ResultSet resultSet) throws SQLException {
        return new Book(resultSet.getLong("id")
                ,resultSet.getString("title")
                ,resultSet.getObject("price", BigDecimal.class));
    }
}
