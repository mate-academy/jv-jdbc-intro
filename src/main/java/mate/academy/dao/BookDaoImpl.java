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
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int FIRST_INDEX = 1;
    private static final int SECOND_INDEX = 2;
    private static final int THIRD_INDEX = 3;
    private static final int AT_LEAST_ONE_ROW = 1;
    private static final int ZERO_ROWS = 0;
    private static final String DELETE_BY_ID_REQUEST = "DELETE FROM books WHERE id = ?";
    private static final String UPDATE_REQUEST
            = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String FIND_ALL_REQUEST = "SELECT * FROM books";
    private static final String FIND_BY_ID_REQUEST = "SELECT * FROM books WHERE id = ?";
    private static final String CREATION_REQUEST = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String TITLE = "title";
    private static final String ID = "id";
    private static final String PRICE = "price";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(CREATION_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_INDEX, book.getPrice());
            if (statement.executeUpdate() < AT_LEAST_ONE_ROW) {
                throw new RuntimeException("Excepted "
                        + "to insert at least one row, but inserted 0 rows.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(FIRST_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_REQUEST)) {
            statement.setLong(FIRST_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(id);
                book.setTitle(resultSet.getString(TITLE));
                book.setPrice(resultSet.getObject(PRICE, BigDecimal.class));
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t find book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_REQUEST)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject(ID, Long.class));
                book.setTitle(resultSet.getString(TITLE));
                book.setPrice(resultSet.getObject(PRICE, BigDecimal.class));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't receive books.", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_REQUEST)) {
            statement.setString(FIRST_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_INDEX, book.getPrice());
            statement.setLong(THIRD_INDEX, book.getId());
            int affectedRow = statement.executeUpdate();
            if (affectedRow < AT_LEAST_ONE_ROW) {
                throw new RuntimeException("Expected at least one row, but inserted 0 rows.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update the book: " + book.getTitle(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_REQUEST)) {
            statement.setLong(FIRST_INDEX, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > ZERO_ROWS;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete book by id: " + id, e);
        }
    }
}
