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
import mate.academy.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String labelId = "id";
    private static final String labelTitle = "title";
    private static final String labelPrice = "price";
    private static final String SQL_CREATE = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM books";
    private static final String SQL_UPDATE = "UPDATE books SET title = ?,"
            + " price = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_CREATE,
                         Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int rows = statement.executeUpdate();
            if (rows < 1) {
                throw new RuntimeException("Expected to insert "
                        + "at least 1 row, but inserted 0 rows.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert new book to the DB", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find the book by index " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {

        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(parseBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create connection to the DB", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            Optional<Book> oldBook = findById(book.getId());

            statement.executeUpdate();

            return oldBook.orElse(null);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update the book "
                    + book.getTitle(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            statement.setLong(1, id);

            int rowToBeDeleted = statement.executeUpdate();

            return rowToBeDeleted > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete the book with id "
                    + id, e);
        }
    }

    private Book parseBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(labelId, Long.class);
        String title = resultSet.getObject(labelTitle, String.class);
        BigDecimal price = resultSet.getObject(labelPrice, BigDecimal.class);
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
