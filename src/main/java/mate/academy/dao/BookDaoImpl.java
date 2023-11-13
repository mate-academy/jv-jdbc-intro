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
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.utils.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_PRICE = "price";
    private static final int PARAM_INDEX_ONE = 1;
    private static final int PARAM_INDEX_TWO = 2;
    private static final int PARAM_INDEX_THREE = 1;
    private static final int MIN_AFFECTED_ROWS = 1;

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book (title, price) VALUES (?, ?)";
        String errorMessage = "Expected to insert at least one row";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(PARAM_INDEX_ONE, book.getTitle());
            statement.setBigDecimal(PARAM_INDEX_TWO, book.getPrice());
            int affectedRows = statement.executeUpdate();
            checkAffectedRows(affectedRows,errorMessage);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(PARAM_INDEX_ONE, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM BOOK WHERE id =?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(PARAM_INDEX_ONE, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString(COLUMN_TITLE);
                BigDecimal price = BigDecimal.valueOf(resultSet.getObject(COLUMN_PRICE,
                        Double.class));
                Book book = createBookObject(id, title, price);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find the book with given id", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM BOOK";
        List<Book> list = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getObject(COLUMN_ID, Long.class);
                String title = resultSet.getString(COLUMN_TITLE);
                BigDecimal price = BigDecimal.valueOf(resultSet.getObject(COLUMN_PRICE,
                        Double.class));
                Book book = createBookObject(id, title, price);
                list.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get the books", e);
        }
        return list;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE book SET title = ?, price = ? WHERE id = ?";
        String errorMessage = "Expected to update at least 1 row";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(PARAM_INDEX_THREE, book.getId());
            statement.setString(PARAM_INDEX_ONE, book.getTitle());
            statement.setBigDecimal(PARAM_INDEX_TWO, book.getPrice());
            int affectedRows = statement.executeUpdate();
            checkAffectedRows(affectedRows, errorMessage);
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Cant update the book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(PARAM_INDEX_ONE, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with given id: " + id, e);
        }
    }

    private Book createBookObject(Long id, String title, BigDecimal price) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }

    private boolean checkAffectedRows(int rows, String message) throws RuntimeException {
        if (rows < MIN_AFFECTED_ROWS) {
            throw new RuntimeException(message);
        } else {
            return true;
        }
    }
}
