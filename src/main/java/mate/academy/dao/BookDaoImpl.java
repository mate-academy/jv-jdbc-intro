package mate.academy.dao;

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
import mate.academy.service.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String INSERT_SQL = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM books WHERE id = ?";
    private static final String SELECT_ALL_BOOKS_SQL = "SELECT * FROM books";
    private static final String UPDATE_SQL = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM books WHERE id = ?";
    private static int FIRST_PARAMETER_INDRX = 1;
    private static int SECOND_PARAMETER_INDRX = 2;
    private static int THIRD_PARAMETER_INDRX = 3;
    private static final int FIRST_COLUMN_INDEX = 1;
    private static final String ID_LABEL = "id";
    private static final String TITLE_LABEL = "title";
    private static final String PRICE_LABEL = "price";
    private static int MIN_AFFECTED_ROWS = 1;

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_SQL,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER_INDRX, book.getTitle());
            statement.setObject(SECOND_PARAMETER_INDRX, book.getPrice());
            checkAffectedRows(statement.executeUpdate());

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(FIRST_COLUMN_INDEX);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setLong(FIRST_PARAMETER_INDRX, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return Optional.of(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find a book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOKS_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(FIRST_PARAMETER_INDRX, book.getTitle());
            statement.setBigDecimal(SECOND_PARAMETER_INDRX, book.getPrice());
            statement.setLong(THIRD_PARAMETER_INDRX, book.getId());
            checkAffectedRows(statement.executeUpdate());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update a book: " + book, e);
        }
        return findById(book.getId()).orElse(book);
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(FIRST_COLUMN_INDEX, id);
            int affectedRows = statement.executeUpdate();
            checkAffectedRows(affectedRows);
            return affectedRows >= MIN_AFFECTED_ROWS;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete a book by id : " + id, e);
        }
    }

    private void checkAffectedRows(int affectedRows) {
        if (affectedRows < MIN_AFFECTED_ROWS) {
            throw new RuntimeException("Expected to affect at least 1 row, but none was affected");
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject(ID_LABEL, Long.class));
        book.setTitle(resultSet.getString(TITLE_LABEL));
        book.setPrice(resultSet.getBigDecimal(PRICE_LABEL));
        return book;
    }
}
