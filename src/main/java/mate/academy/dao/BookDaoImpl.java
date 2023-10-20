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
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setLong(2, book.getPrice().longValue());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a new book", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = new Book();
        String sql = "SELECT * FROM books WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = conversion(resultSet);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't find by id " + id, e);
        }
        return Optional.of(book);
    }

    @Override
    public List<Book> findAll() {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement =
                        connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Book book = conversion(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ? AND is_deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Book" + book + "can't update", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "UPDATE books SET is_deleted = 1 WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Book can't delete by ID " + id, e);
        }
    }

    private Book conversion(ResultSet resultSet) {
        try {
            Long bookId = resultSet.getObject(1, Long.class);
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getBigDecimal("price");
            return new Book(bookId, title, price);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t conversion resultSet " + resultSet, e);
        }
    }
}
