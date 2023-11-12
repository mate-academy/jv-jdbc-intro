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

    private static final String TITLE = "title";
    private static final String PRICE = "price";
    private static final String ID = "id";
    private static final String EXPECTED_CREATE =
            "Expected to insert leas one row, but inserted 0 rows.";
    private static final String ZERO_ROW = "Expected to insert leas one row, but inserted 0 rows.";
    private static final String NOT_ONE_FIND = "Can not find the book";
    private static final String NOT_ALL_FIND = "Can't find all books";
    private static final String DELETE_EXCEPTION = "Unable to delete a book with an id: ";
    private static final String NOT_UPDATE = "Can not update the book: ";
    private static final String TITLE_PRICE_VALUE = "INSERT INTO books (title, price) VALUE (?,?)";
    private static final String SELECT_FROM_BOOKS_WHERE_ID = "SELECT * FROM books WHERE id = ?";
    private static final String SELECT_FROM_BOOKS = "SELECT * FROM books";
    private static final String SET_TITLE_PRICE_WHERE_ID =
            "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_FROM_BOOKS_ID = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(TITLE_PRICE_VALUE,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException(ZERO_ROW);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(EXPECTED_CREATE, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connectionUtil = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connectionUtil
                    .prepareStatement(SELECT_FROM_BOOKS_WHERE_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString(TITLE);
                BigDecimal price = resultSet.getObject(PRICE, BigDecimal.class);
                return Optional.of(new Book(id, title, price));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(NOT_ONE_FIND, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_FROM_BOOKS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getObject(ID, Long.class);
                String title = resultSet.getString(TITLE);
                BigDecimal price = resultSet.getObject(PRICE, BigDecimal.class);
                bookList.add(new Book(id, title, price));
            }

        } catch (SQLException e) {
            throw new RuntimeException(NOT_ALL_FIND, e);
        }
        return bookList;
    }

    @Override
    public Optional<Book> update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SET_TITLE_PRICE_WHERE_ID);
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setObject(3, book.getId());
            int i = statement.executeUpdate();
            if (i > 1) {
                return Optional.of(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException(NOT_UPDATE + book, e);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_FROM_BOOKS_ID);
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(DELETE_EXCEPTION + id, e);
        }
    }
}
