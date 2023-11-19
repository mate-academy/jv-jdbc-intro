package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setObject(2,book.getPrice());
            int rows = statement.executeUpdate();
            if (rows < 1) {
                throw new RuntimeException("Inserted 0 rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create a new book" + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        Book book = new Book();
        Optional<Book> result;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                book.setId(resultSet.getLong(1));
                book.setTitle(resultSet.getString(2));
                book.setPrice(resultSet.getBigDecimal(3));
            }
            result = Optional.of(book);

        } catch (SQLException e) {
            throw new RuntimeException("Record not found.", e);
        }
        return result;
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        Book book = new Book();
        List<Book> result = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                book.setId(resultSet.getLong(1));
                book.setTitle(resultSet.getString(2));
                book.setPrice(resultSet.getBigDecimal(3));
                result.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("No record found.", e);
        }
        return result;
    }

    @Override
    public Book update(Book book) {
        String sql = "INSERT INTO book (id, title, price) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            deleteById(book.getId());
            statement.setLong(1, book.getId());
            statement.setString(2, book.getTitle());
            statement.setObject(3, book.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can`t update book, " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1,id);
            if (statement.executeUpdate() <= 0) {
                throw new RuntimeException("Record not found.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Record not found.", e);
        }
        return true;
    }
}
