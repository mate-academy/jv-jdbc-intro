package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lib.Dao;
import mate.academy.ConnectionUtil;
import model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Dao
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to affect at least one row");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t add a new book: " + book, e);
        }
        return book;
    }

    @Dao
    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement preparedStatement =
                     ConnectionUtil.getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(BigDecimal.valueOf(resultSet.getInt("price")));
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t connect to database", e);
        }
        return Optional.empty();
    }

    @Dao
    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        try (PreparedStatement preparedStatement =
                     ConnectionUtil.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Book> booksList = new ArrayList<>();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(BigDecimal.valueOf(resultSet.getInt("price")));
                booksList.add(book);
            }
            return booksList;
        } catch (SQLException e) {
            throw new RuntimeException("Can`t connect to database", e);
        }
    }

    @Dao
    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?"; // Коррекция
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice()); // Коррекция
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("No book found with ID: " + book.getId()); // Коррекция
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t connect to database", e);
        }
        return book;
    }

    @Dao
    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete book with id: " + id, e);
        }
    }
}
