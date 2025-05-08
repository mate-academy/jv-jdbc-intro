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
import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException(
                    "Expected to insert at least 1 row, but inserted 0 row.");
            }

            ResultSet generatedSet = statement.getGeneratedKeys();
            if (generatedSet.next()) {
                Long id = generatedSet.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(setBookData(id, resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book with ID " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                books.add(setBookData(id, resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books from DB", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        Long bookId = book.getId();
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            statement.setObject(3, bookId);
            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException(
                    "Expected to update at least 1 row, but updated 0 row.");
            }

            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book with ID " + bookId, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with ID " + id, e);
        }
    }

    private Book setBookData(Long id, ResultSet resultSet) throws SQLException {
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");

        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);

        return book;
    }
}
