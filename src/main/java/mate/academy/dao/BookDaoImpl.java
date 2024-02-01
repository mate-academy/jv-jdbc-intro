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
    private static final String UPDATE_QUERY = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String CREATE_QUERY = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM books WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM books";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PRICE = "price";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException("Expected to create at least one row");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can not create new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(convertToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find nothing by that id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> listOfBooks = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                listOfBooks.add(convertToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get list of books ", e);
        }
        return listOfBooks;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setLong(3, book.getId());
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException("Expected to update at least one row");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get update book " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete by id" + id, e);
        }
    }

    private Book convertToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject(ID, Long.class));
        book.setTitle(resultSet.getString(TITLE));
        book.setPrice(resultSet.getObject(PRICE, BigDecimal.class));
        return book;
    }
}
