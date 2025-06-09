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
    private static final String ID_COLUMN = "id";
    private static final String TITLE_COLUMN = "title";
    private static final String PRICE_COLUMN = "price";
    private static final String CREATE_QUERY = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM books WHERE id = ? "
            + "AND is_deleted = FALSE";
    private static final String FIND_ALL_QUERIES = "SELECT * FROM books "
            + "WHERE is_deleted = FALSE";
    private static final String UPDATE_QUERY = "UPDATE books SET title = ?, price = ? WHERE id = ? "
            + "AND is_deleted = FALSE";
    private static final String DELETE_QUERY = "UPDATE books SET is_deleted = TRUE WHERE id = ? "
            + "AND is_deleted = FALSE";

    @Override
    public Book create(Book book) {
        if (book == null) {
            return null;
        }
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least 1 row, "
                        + "but inserted 0 rows");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(FIND_ALL_QUERIES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books from DB", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            if (preparedStatement.executeUpdate() > 0) {
                return book;
            } else {
                throw new RuntimeException("At least one row should have been updated,"
                        + " but none were updated");
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't update new book " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id " + id, e);
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(ID_COLUMN, Long.class);
        String title = resultSet.getString(TITLE_COLUMN);
        BigDecimal price = resultSet.getBigDecimal(PRICE_COLUMN);

        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
