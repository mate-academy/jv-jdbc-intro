package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.connection.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row,"
                        + " but inserted zero rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection to the DB ", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookList.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get any books ", e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to update at least one row,"
                        + " but updated zero rows");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update a book: " + book, e);
        }
        return book;
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
            throw new DataProcessingException("Can't create connection to the DB ", e);
        }
    }

    private static Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getObject("price", Integer.class));
        return book;
    }
}
