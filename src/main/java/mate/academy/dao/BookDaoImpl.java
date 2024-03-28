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
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_PRICE = "price";

    @Override
    public Book create(Book book) {
        String insertQuery = "INSERT INTO books (title, price) VALUES(?, ?);";
        try (Connection connection = DatabaseConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(insertQuery,
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
        String selectByIdQuery = "SELECT * FROM books WHERE id = ?;";
        try (Connection connection = DatabaseConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectByIdQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Finding book by id failed: id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String selectAllQuery = "SELECT * FROM books;";
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(selectAllQuery)) {
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
        String updateQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?;";
        try (Connection connection = DatabaseConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(updateQuery)) {
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
        String deleteByIdQuery = "DELETE FROM books WHERE id = ?;";
        try (Connection connection = DatabaseConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(deleteByIdQuery)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Deleting book failed: id " + id, e);
        }
    }

    private void checkUpdate(int affectedRows, String errorMessage) {
        if (affectedRows < 1) {
            throw new DataProcessingException(errorMessage);
        }
    }

    private Book mapToBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(COLUMN_ID);
        String title = resultSet.getString(COLUMN_TITLE);
        BigDecimal price = resultSet.getBigDecimal(COLUMN_PRICE);
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
