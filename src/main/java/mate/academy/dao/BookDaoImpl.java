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
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {

    public static final String INSERT_QUERY = "INSERT INTO books (title,price) VALUES(?,?)";
    public static final String FIND_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    public static final String SELECT_ALL_QUERY = "SELECT * FROM books";
    public static final String UPDATE_QUERY = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    public static final String DELETE_QUERY = "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException("Creating book failed, no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = mapToBook(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find book by id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(mapToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find all books from table", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            statement.setLong(3, book.getId());
            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException(
                        String.format("Book with id=%d wasn't updated, no rows affected.",
                                book.getId()));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book " + book);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id " + id);
        }
    }

    private Book mapToBook(ResultSet resultSet) throws SQLException {
        return new Book(resultSet.getObject("id", Long.class),
                resultSet.getString("title"),
                resultSet.getBigDecimal("price"));
    }
}
