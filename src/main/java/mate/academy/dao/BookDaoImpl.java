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
    private static final int TITLE_INDEX = 1;
    private static final int PRICE_INDEX = 2;
    private static final int ID_INDEX = 3;
    private static final int ID_PARAMETER_INDEX = 1;
    private static final int GENERATED_KEY_INDEX = 1;
    private static final int MIN_AFFECTED_ROWS = 1;
    private static final int NO_ROWS_AFFECTED = 0;

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < MIN_AFFECTED_ROWS) {
                throw new DataProcessingException("Expected paste at least 1 row, "
                        + "but was pasted 0 rows.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(GENERATED_KEY_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot save a book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Optional<Book> result = Optional.empty();
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(ID_PARAMETER_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = Optional.of(parseBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find result with id:" + id, e);
        }
        return result;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(parseBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find books in Database.", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(ID_INDEX, book.getId());
            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < MIN_AFFECTED_ROWS) {
                throw new DataProcessingException("Expected update at least 1 row, "
                        + "but was updated 0 rows.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to find book to update with id:"
                    + book.getId() + " in database.", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(ID_PARAMETER_INDEX, id);
            return statement.executeUpdate() > NO_ROWS_AFFECTED;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete book with id:" + id, e);
        }
    }

    private Book parseBook(ResultSet resultSet) {
        Book book;
        try {
            Long id = resultSet.getObject("id", Long.class);
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getObject("price", BigDecimal.class);
            book = new Book(id, title, price);
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot parse data to Book", e);
        }
        return book;
    }
}
