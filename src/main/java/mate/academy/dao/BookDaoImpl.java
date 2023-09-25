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
    public static final String TABLE_NAME = "books";
    private static final int FIRST_EL_SQL_QUERY = 1;
    private static final int SECOND_EL_SQL_QUERY = 2;
    private static final int THIRD_EL_SQL_QUERY = 3;
    private static final int ZERO_ELEMENT = 0;

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO " + TABLE_NAME + " (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_EL_SQL_QUERY, book.getTitle());
            statement.setBigDecimal(SECOND_EL_SQL_QUERY, book.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(FIRST_EL_SQL_QUERY, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not add book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(FIRST_EL_SQL_QUERY, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = createBook(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can not create connection to DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement = connection.prepareStatement(sql)) {
            List<Book> books = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Book book = createBook(resultSet);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("Can not create connection to DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE " + TABLE_NAME + " SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(FIRST_EL_SQL_QUERY, book.getTitle());
            statement.setBigDecimal(SECOND_EL_SQL_QUERY, book.getPrice());
            statement.setLong(THIRD_EL_SQL_QUERY, book.getId());
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
            statement.setLong(FIRST_EL_SQL_QUERY, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > ZERO_ELEMENT;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id, e);
        }
    }

    private Book createBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(FIRST_EL_SQL_QUERY, Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        Book book = new Book();
        book.setId(id);
        book.setPrice(price);
        book.setTitle(title);
        return book;
    }
}
