package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.connection.util.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CREATE_QUERY = "INSERT INTO BOOKS (title, price) VALUES(?, ?)";
    private static final String SELECT_All_QUERY = "SELECT * FROM books";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE books SET title=?, price=? WHERE id=?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM books WHERE id=?";
    private static final String ID_COLUMN = "id";
    private static final String TITLE_COLUMN = "title";
    private static final String PRICE_COLUMN = "price";
    private static final int MIN_AFFECTED_ROWS = 1;

    @Override
    public Book create(Book book) {
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            checkAffectedRows(statement.executeUpdate());
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to put the book into the db " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (PreparedStatement statement = createStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return Optional.of(parseBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to find the book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        try (PreparedStatement statement = createStatement(SELECT_All_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(parseBookFromResultSet(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to select all books form DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (PreparedStatement statement = createStatement(UPDATE_QUERY)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
            checkAffectedRows(affectedRows);
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update the book " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement statement = createStatement(DELETE_BY_ID_QUERY)) {
            statement.setLong(1, id);
            return statement.executeUpdate() >= MIN_AFFECTED_ROWS;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete book by id " + id, e);
        }
    }

    private Book parseBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book newBook = new Book();
        newBook.setId(resultSet.getObject(ID_COLUMN, Long.class));
        newBook.setTitle(resultSet.getObject(TITLE_COLUMN, String.class));
        newBook.setPrice(resultSet.getObject(PRICE_COLUMN, BigDecimal.class));
        return newBook;
    }

    private void checkAffectedRows(int rows) throws SQLException {
        if (rows < MIN_AFFECTED_ROWS) {
            throw new SQLException();
        }
    }

    private PreparedStatement createStatement(String query) throws SQLException {
        return ConnectionUtil.getConnection().prepareStatement(query);
    }
}
