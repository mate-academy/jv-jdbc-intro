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
import mate.academy.ConnectionUtil;
import mate.academy.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int PARAMETER_INDEX = 1;
    private static final int SECOND_PARAMETER_INDEX = 2;
    private static final int THIRD_PARAMETER_INDEX = 3;

    @Override
    public Book create(Book book) {
        String sqlCall = "INSERT INTO books(title, price) VALUES(?, ?)";
        String title = book.getTitle();
        BigDecimal price = book.getPrice();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(sqlCall, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(PARAMETER_INDEX, title);
            statement.setBigDecimal(SECOND_PARAMETER_INDEX, price);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row, "
                        + "but inserted 0 rows.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(PARAMETER_INDEX, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlCall = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlCall)) {
            statement.setLong(PARAMETER_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(setBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create a connection to thr DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sqlCall = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlCall)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = setBook(resultSet);
                book.setId(book.getId());
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create a connection to thr DB", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sqlCall = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlCall)) {
            statement.setString(PARAMETER_INDEX, book.getTitle());
            statement.setObject(SECOND_PARAMETER_INDEX, book.getPrice());
            statement.setObject(THIRD_PARAMETER_INDEX, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Entered ID was not a valid: " + book.getId());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create a connection to thr DB", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlCall = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlCall)) {
            statement.setLong(PARAMETER_INDEX, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to delete at least one row, "
                        + "but deleted 0 rows.");
            }
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete book", e);
        }
    }

    private Book setBook(ResultSet resultSet) throws SQLException {
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        Book book = new Book();
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
