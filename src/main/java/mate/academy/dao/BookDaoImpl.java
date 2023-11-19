package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.connectionutil.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int FIRST_INDEX = 1;
    private static final int SECOND_INDEX = 2;
    private static final int THIRD_INDEX = 3;
    private static final int ZERO_ROW = 0;
    private static final String INSERT_SQL = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM books WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM books";
    private static final String UPDATE_SQL = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_INDEX, book.getPrice());
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(FIRST_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add a new book: " + book, e);
        }
        return book;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(UPDATE_SQL)) {
            statement.setString(FIRST_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_INDEX, book.getPrice());
            statement.setLong(THIRD_INDEX, book.getId());
            statement.executeUpdate();
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("db don't have needed row", new SQLException());
            }
            return book;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't update the book "
                    + book.getTitle(), e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setLong(FIRST_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next()
                    ? Optional.of(getBookFromResultSet(resultSet))
                    : Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get a book by id: " + id, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(FIRST_INDEX, id);
            return statement.executeUpdate() > ZERO_ROW;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find books!", e);
        }
        return books;

    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }
}
