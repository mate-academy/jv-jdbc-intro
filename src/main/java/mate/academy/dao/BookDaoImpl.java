package mate.academy.dao;

import java.math.BigDecimal;
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
    private static final String CREATION_EXCEPTION_MESSAGE = "Expected to insert 1 row,"
                                                            + " but inserted 0 rows";
    private static final String WRONG_ID_MESSAGE = "Cant find a book with id = ";
    private static final String ID_COLUMN = "id";
    private static final String TITLE_COLUMN = "title";
    private static final String PRICE_COLUMN = "price";

    private static final int MIN_AFFECTED_ROWS = 1;
    private static final String CREATE_QUERY = "INSERT INTO books (title, price) VALUES(?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM books";
    private static final String UPDATE_QUERY = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        String title = book.getTitle();
        double price = book.getPrice().doubleValue();
        try (PreparedStatement statement = ConnectionUtil.makeConnection()
                .prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, title);
            statement.setDouble(2, price);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < MIN_AFFECTED_ROWS) {
                throw new DataProcessingException(CREATION_EXCEPTION_MESSAGE);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to put the book into the db");
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (PreparedStatement statement = ConnectionUtil.makeConnection()
                .prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapFromResultToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(WRONG_ID_MESSAGE + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> listOfBooks = new ArrayList<>();
        try (PreparedStatement statement = ConnectionUtil.makeConnection()
                    .prepareStatement(FIND_ALL_QUERY);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                listOfBooks.add(mapFromResultToBook(resultSet));
            }
            return listOfBooks;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (PreparedStatement statement = ConnectionUtil.makeConnection()
                .prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, book.getTitle());
            statement.setDouble(2, book.getPrice().doubleValue());
            statement.setLong(3, book.getId());
            if (statement.executeUpdate() < MIN_AFFECTED_ROWS) {
                throw new DataProcessingException("Failed to update object with such id = "
                                                                        + book.getId());
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Error updating book", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement statement = ConnectionUtil.makeConnection()
                .prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            return statement.executeUpdate() >= MIN_AFFECTED_ROWS;
        } catch (SQLException e) {
            throw new DataProcessingException(WRONG_ID_MESSAGE + id, e);
        }
    }

    private Book mapFromResultToBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID_COLUMN);
        String title = resultSet.getString(TITLE_COLUMN);
        BigDecimal price = BigDecimal.valueOf(resultSet.getDouble(PRICE_COLUMN));
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
