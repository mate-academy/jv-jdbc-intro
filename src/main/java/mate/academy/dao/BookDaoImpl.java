package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Optional<Book> findById(Long id) {
        String selectQuery = "SELECT * FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(selectQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create connection to the DB", e);
        }
        return Optional.empty();
    }

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row,"
                        + " but inserted 0 rows.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't add new book: "
                    + book, e);
        }
        return book;
    }

    @Override
    public Book update(Book book) {
        String selectQuery = "UPDATE book SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(selectQuery)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to update at least one row,"
                        + "but update 0 rows.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: "
                    + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete book from ID = " + id);
        }
    }

    @Override
    public List<Book> findAll() {
        String selectQuery = "SELECT * FROM book";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(createBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create connection to the DB", e);
        }
        return books;
    }

    private Book createBook(ResultSet resultSet) throws SQLException {
        return new Book(resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getBigDecimal("price"));
    }
}
