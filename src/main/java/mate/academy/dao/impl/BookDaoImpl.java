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
        String createBookStatement = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(createBookStatement,
                                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to insert at least one row,"
                        + " but inserted 0 rows.", new RuntimeException());
            }
            ResultSet generationKey = preparedStatement.getGeneratedKeys();
            if (generationKey.next()) {
                long id = generationKey.getLong(1);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't save a new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String findBookByIdStatement = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(findBookByIdStatement)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(extractBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a connection to the database", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String findBookByIdStatement = "SELECT * FROM books";
        List<Book> foundBooks = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(findBookByIdStatement)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                foundBooks.add(extractBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a connection to the database", e);
        }
        return foundBooks;
    }

    @Override
    public Book update(Book book) {
        String updateBookStatement = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(updateBookStatement)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Can't update the book: " + book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update the book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete the book by id = " + id, e);
        }
    }

    private Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        return new Book(id, title, price);
    }
}
