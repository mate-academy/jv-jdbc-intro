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
    private static final int COLUMN_ID = 1;
    private static final int COLUMN_TITLE = 2;
    private static final int COLUMN_PRICE = 3;

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(
                        "Expected to insert at less one row, but inserted 0 rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add a new book, " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, BigDecimal.valueOf(id));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(getBookFromResulSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't find a book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                books.add(getBookFromResulSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't find books in the table ", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to update one row, but updated 0 rows");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update a book by the id: " + book.getId(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, BigDecimal.valueOf(id));
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete the book by id: " + id, e);
        }
    }

    private Book getBookFromResulSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong(COLUMN_ID));
        book.setTitle(resultSet.getString(COLUMN_TITLE));
        book.setPrice(resultSet.getBigDecimal(COLUMN_PRICE));
        return book;
    }
}
