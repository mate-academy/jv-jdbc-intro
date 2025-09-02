package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long idFromDb = resultSet.getObject("id", Long.class);
                if (idFromDb == null) {
                    throw new RuntimeException("Invalid id");
                }
                if (Objects.equals(id, idFromDb)) {
                    String title = resultSet.getNString("title");
                    BigDecimal price = resultSet.getBigDecimal("price");

                    Book book = new Book();
                    book.setId(id);
                    book.setTitle(title);
                    book.setPrice(price);
                    return Optional.of(book);
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to find by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setNString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            if (book.getId() == null) {
                throw new RuntimeException("ID cannot be null.");
            }
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected < 0) {
                throw new RuntimeException("Book with id: " + book.getId() + " weren't updated");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update item from DB", e);
        }
    }

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            System.out.println("Rows affected: " + affectedRows);
            if (affectedRows < 1) {
                throw new RuntimeException("Affected rows cannot be less than 1!");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to insert item into DB", e);
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject("id", Long.class));
                book.setTitle(resultSet.getNString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to fetch books from DB", e);
        }

        return books;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete by ID from DB.", e);
        }
    }
}
