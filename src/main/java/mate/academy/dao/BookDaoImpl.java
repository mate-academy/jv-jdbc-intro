package mate.academy.dao;

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

    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getObject("id", Long.class));
                return book;
            } else {
                throw new DataProcessingException("Can not create book - no generated keys returned",
                        null);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create book " + book, e);
        }
    }

    public Optional<Book> findById(Long id) {
        String sql = "SELECT id, title, price FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getBook(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't find book by id: " + id, e);
        }

        return Optional.empty();
    }

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection connection = ConnectionUtil.getConnection();
               PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(getBook(resultSet));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can not find all books",e);
        }
        return books;
    }

    public Book update (Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
               PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update book with " + book.getId()
                    + "id ",e);
        }
        return null;
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
               PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete book with " + id + "id ",e);
        }
    }

    private Book getBook(ResultSet resultSet) throws SQLException {
        return new Book(resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getBigDecimal("price"));
    }
}
