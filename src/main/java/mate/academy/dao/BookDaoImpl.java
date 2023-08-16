package mate.academy.dao;

import mate.academy.ConnectionUtil;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    private final static String INSERT = "INSERT INTO books (title,price) VALUES (?, ?)";
    private final static String SELECT_ALL = "SELECT * FROM books";
    private final static String UPDATE = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private final static String SELECT_BY_ID = "SELECT * FROM books WHERE id = ?";
    private final static String DELETE_BY_ID = "DELETE FROM books WHERE id = ?";
    private static final int ID_INDEX = 1;
    private static final int TITLE_POSITION = 1;
    private static final int PRICE_POSITION = 2;
    private static final int MINIMUM_AFFECTED_ROWS = 1;
    private static final int ID_POSITION = 3;

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(TITLE_POSITION, book.getTitle());
            statement.setBigDecimal(PRICE_POSITION, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < MINIMUM_AFFECTED_ROWS) {
                throw new DataProcessingException("Can't insert less than one row");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_INDEX, Long.class);
                book.setId(id);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert book: " + book);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                Book book = getBookFromResultSet(resultSet);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books");
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(ID_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = getBookFromResultSet(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(TITLE_POSITION, book.getTitle());
            statement.setBigDecimal(PRICE_POSITION, book.getPrice());
            statement.setLong(ID_POSITION, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < MINIMUM_AFFECTED_ROWS) {
                throw new DataProcessingException("Can't update less than one row");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update a book " + book);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {
            statement.setLong(ID_INDEX, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id , e);
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        Book book = new Book(id, title, price);
        return book;
    }
}
