package mate.academy.dao;

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
    private static final String CREATE_BOOK_QUERY = "INSERT INTO books(title, price) VALUES(?, ?);";
    private static final String FIND_BOOK_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?;";
    private static final String FIND_ALL_BOOKS_QUERY = "SELECT * FROM books";
    private static final String UPDATE_BOOK_QUERY
            = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BOOK_QUERY = "DELETE FROM books WHERE id = ?";
    private static final int FIRST_PARAMETER_INDEX = 1;
    private static final int SECOND_PARAMETER_INDEX = 2;
    private static final int THIRD_PARAMETER_INDEX = 3;
    private static final int MINIMUM_AFFECTED_ROWS = 1;
    private static final int FIRST_COLUMN_INDEX = 1;
    private static final int Zero_ROW = 0;

    private static final int RETURN_GENERATED_KEYS = Statement.RETURN_GENERATED_KEYS;

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                         connection.prepareStatement(CREATE_BOOK_QUERY, RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_PARAMETER_INDEX, book.getPrice());
            checkAffectedRows(statement.executeUpdate());
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getObject(FIRST_COLUMN_INDEX, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add book to DB: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(FIND_BOOK_BY_ID_QUERY)) {
            statement.setLong(FIRST_PARAMETER_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = getBookFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book with id: " + id, e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(FIND_ALL_BOOKS_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get books from DB", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK_QUERY)) {
            statement.setString(FIRST_PARAMETER_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_PARAMETER_INDEX, book.getPrice());
            statement.setLong(THIRD_PARAMETER_INDEX, book.getId());
            checkAffectedRows(statement.executeUpdate());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(DELETE_BOOK_QUERY)) {
            statement.setLong(FIRST_PARAMETER_INDEX, id);
            return statement.executeUpdate() > Zero_ROW;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id, e);
        }
    }

    private static void checkAffectedRows(int affectedRows) {
        if (affectedRows < MINIMUM_AFFECTED_ROWS) {
            throw new RuntimeException("Affected rows should be more than 0");
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }
}
