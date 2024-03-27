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
import mate.academy.util.DatabaseConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES(?, ?);";
        try (Connection connection = DatabaseConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            checkUpdate(affectedRows, "Failed to create book: " + book.getTitle());
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Creating book failed: " + book.getTitle(), e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?;";
        try (Connection connection = DatabaseConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Finding book failed: id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books;";
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(mapToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Retrieving all books failed", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?;";
        try (Connection connection = DatabaseConnectionUtil.getConnection();
                   PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            checkUpdate(affectedRows,
                    "Failed to update book: id = " + book);
        } catch (SQLException e) {
            throw new DataProcessingException("Updating book failed: " + book.getTitle(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?;";
        try (Connection connection = DatabaseConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                return false;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Deleting book failed: id " + id, e);
        }
        return true;
    }

    private void checkUpdate(int affectedRows, String errorMessage) {
        if (affectedRows < 1) {
            throw new DataProcessingException(errorMessage);
        }
    }

    private Book mapToBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
