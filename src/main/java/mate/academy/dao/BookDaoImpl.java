package mate.academy.dao;

import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String TABLE_NAME = "books";
    private static final String TITLE_NAME = "title";
    private static final String PRICE_NAME = "price";
    private static final int FIRST_INDEX_SQL_DATA = 1;
    private static final int SECOND_INDEX_SQL_DATA = 2;
    private static final int THIRD_INDEX_SQL_DATA = 3;
    private static final int ZERO_NUM = 0;

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO " + TABLE_NAME + " (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_INDEX_SQL_DATA, book.getTitle());
            statement.setBigDecimal(SECOND_INDEX_SQL_DATA, book.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(FIRST_INDEX_SQL_DATA, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(FIRST_INDEX_SQL_DATA, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = createBook(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create a connection to the DB" ,e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Book> allBooks = new ArrayList<>();
            while (resultSet.next()) {
                Book book = createBook(resultSet);
                allBooks.add(book);
            }
            return allBooks;
        } catch (SQLException e) {
            throw new RuntimeException("Can't create a connection to the DB" ,e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE " + TABLE_NAME + " SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(FIRST_INDEX_SQL_DATA, book.getTitle());
            statement.setBigDecimal(SECOND_INDEX_SQL_DATA, book.getPrice());
            statement.setLong(THIRD_INDEX_SQL_DATA, book.getId());
            statement.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update new book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(FIRST_INDEX_SQL_DATA, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > ZERO_NUM;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id, e);
        }
    }

    private Book createBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(FIRST_INDEX_SQL_DATA, Long.class);
        String title = resultSet.getString(TITLE_NAME);
        BigDecimal price = resultSet.getObject(PRICE_NAME, BigDecimal.class);
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
