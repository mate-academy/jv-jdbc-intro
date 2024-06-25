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
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(
                        "Expected to insert at least one row, but inserted 0 rows.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                Book book = mapResultSetToBook(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find the book by id, where id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                do {
                    Book book = mapResultSetToBook(resultSet);
                    books.add(book);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all from table", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(
                        "Expected to update at least one row, but updated 0 rows.");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update the book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (
                Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(
                        "Expected to delete at least one row, but deleted 0 rows.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book where id = " + id, e);
        }
        return true;
    }
    
    private static Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getObject("title", String.class));
        book.setPrice(resultSet.getObject("price", BigDecimal.class));
        return book;
    }
}
