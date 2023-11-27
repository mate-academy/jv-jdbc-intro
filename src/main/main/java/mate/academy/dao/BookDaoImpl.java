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
import mate.academy.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = new ConnectionUtil().getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at leas on row"
                        + ", but inserted 0 rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long newId = generatedKeys.getObject(1, Long.class);
                return new Book(newId, book.getTitle(), book.getPrice());
            }
            throw new RuntimeException("Creating book failed, no ID obtained.");
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create connection to DB", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = new ConnectionUtil().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                Book book = new Book(id, title, price);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create connection to DB", e);
        }
        return Optional.empty();

    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = new ConnectionUtil().getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                Book book = new Book(id, title, price);
                books.add(book);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Cannot create connection to DB", ex);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?,price = ? WHERE id = ?";
        try (Connection connection = new ConnectionUtil().getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(3, book.getId());
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at leas on row,"
                        + " but inserted 0 rows");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create connection to DB", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = new ConnectionUtil().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int updateRows = statement.executeUpdate();
            return updateRows > 0;

        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create connection to DB", e);
        }
    }
}
