package mate.academy.dao;

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
import mate.academy.service.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String ID_COLUMN = "id";
    private static final String TITLE_COLUMN = "title";
    private static final String PRICE_COLUMN = "price";
    private static final String CREATE_SQL = "INSERT INTO books(title, price) VALUES(?, ?)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM books";
    private static final String UPDATE_SQL = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM books WHERE id = ?";
    private static final int MIN_AFFECTED_ROWS = 1;

    @Override
    public Book create(Book book) {
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                        .prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            checkAffectedRows(statement.executeUpdate());
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a book" + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (PreparedStatement statement = createStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(addBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find a book by ID: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        try (PreparedStatement statement = createStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(addBookFromResultSet(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't find any book", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (PreparedStatement statement = createStatement(UPDATE_SQL)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            checkAffectedRows(statement.executeUpdate());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update information about book: "
                    + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement statement = createStatement(DELETE_BY_ID_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() >= MIN_AFFECTED_ROWS;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete a book with ID: " + id, e);
        }
    }

    private PreparedStatement createStatement(String sql) throws SQLException {
        return ConnectionUtil.getConnection().prepareStatement(sql);
    }

    private void checkAffectedRows(int affectedRows) {
        if (affectedRows < MIN_AFFECTED_ROWS) {
            throw new RuntimeException("Expected at least one row but no rows at all!");
        }
    }

    private Book addBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong(ID_COLUMN));
        book.setTitle(resultSet.getString(TITLE_COLUMN));
        book.setPrice(resultSet.getBigDecimal(PRICE_COLUMN));
        return book;
    }
}
