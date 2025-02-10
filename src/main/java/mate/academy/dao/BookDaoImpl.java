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
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row, but inserted "
                        + affectedRows + " rows.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            } else {
                throw new RuntimeException("Error: Book was inserted but no ID was generated");
            }
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Can't add new book: " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Book(
                        resultSet.getObject("id", Long.class),
                        resultSet.getString("title"),
                        resultSet.getObject("price", BigDecimal.class)
                    ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding book by ID: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                books.add(new Book(
                        resultSet.getObject("id", Long.class),
                        resultSet.getString("title"),
                        resultSet.getObject("price", BigDecimal.class)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all books", e);
        }
        return books;
    }

    @Override
    public Optional<Book> update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            statement.setObject(3, book.getId());

            int updatedRows = statement.executeUpdate();
            if (updatedRows > 0) {
                return Optional.of(book);
            } else {
                throw new RuntimeException("No book was updated with ID: " + book.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating book", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting book with ID: " + id, e);
        }
    }
}
