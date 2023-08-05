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
    private static final String AT_LEAST_ONE_ROW_EXCEPTION
            = "Expected to insert at least one row, but inserted 0 rows";
    private static final String ADD_BOOK_EXCEPTION = "Cannot add new book: ";
    private static final String GET_BOOK_EXCEPTION = "Cannot get a book by id: ";
    private static final String GET_ALL_BOOKS_EXCEPTION = "Cannot get books";
    private static final String UPDATE_BOOK_EXCEPTION = "Cannot update book by id ";
    private static final String DELETE_BOOK_EXCEPTION = "Cannot delete by id ";
    private static final String NO_BOOK_EXCEPTION = "There are no book by id: ";
    private static final String NULL_FIELDS_EXCEPTION = "Title name or price cannot be null";
    private static final String NEGATIVE_ID_EXCEPTION = "ID cannot be negative";
    private static final String NO_RECORDS_EXCEPTION = "There are no records in DB";
    private static final int LESS_THAN_ONE = 1;
    private static final int AT_LEAST_ONE = 0;
    private static final int LESS_THAN_ZERO = 0;
    private static final int ZERO = 0;

    @Override
    public Book create(Book book) {
        if (book.getTitle() == null || book.getPrice() == null) {
            throw new RuntimeException(NULL_FIELDS_EXCEPTION);
        }
        String sqlUpdate = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlUpdate, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < LESS_THAN_ONE) {
                throw new RuntimeException(AT_LEAST_ONE_ROW_EXCEPTION);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(ADD_BOOK_EXCEPTION + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        if (id < LESS_THAN_ZERO) {
            throw new RuntimeException(NEGATIVE_ID_EXCEPTION);
        }
        String sqlQuery = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String model = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                return Optional.of(new Book(id, model, price));
            }
            throw new RuntimeException(NO_BOOK_EXCEPTION + id);
        } catch (SQLException e) {
            throw new DataProcessingException(GET_BOOK_EXCEPTION, e);
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
                Long id = resultSet.getObject("id", Long.class);
                String model = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                books.add(new Book(id, model, price));
            }
            if (books.size() == ZERO) {
                throw new RuntimeException(NO_RECORDS_EXCEPTION);
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException(GET_ALL_BOOKS_EXCEPTION, e);
        }
    }

    @Override
    public Book update(Book book) {
        if (book.getTitle() == null || book.getPrice() == null) {
            throw new RuntimeException(NULL_FIELDS_EXCEPTION);
        }
        String sqlUpdate = "UPDATE books SET price = ?, title = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlUpdate)) {
            statement.setBigDecimal(1, book.getPrice());
            statement.setString(2, book.getTitle());
            statement.setLong(3, book.getId());
            statement.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(UPDATE_BOOK_EXCEPTION + book.getId(), e);
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
            throw new DataProcessingException(DELETE_BOOK_EXCEPTION + id, e);
        }
    }
}
