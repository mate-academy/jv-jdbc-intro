package mate.academy.dao;

import mate.academy.connection.util.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int AT_LEAST_ONE = 0;

    @Override
    public Book create(Book book) {
        String sqlUpdate = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlUpdate, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot add new book: " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;
        String sqlQuery = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = parseBook(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get a book by id: " + id, e);
        }
    }

        @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sqlQuery = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(parseBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sqlUpdate = "UPDATE books SET price = ?, title = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlUpdate)) {
            statement.setBigDecimal(1, book.getPrice());
            statement.setString(2, book.getTitle());
            statement.setLong(3, book.getId());
            statement.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update a book by id " + book.getId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlUpdate = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlUpdate)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > AT_LEAST_ONE;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete a book by id " + id, e);
        }
    }

    private Book parseBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String model = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        return new Book(id, model, price);
    }
}
