package mate.academy.dao;

import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.ConnectionUtil;
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
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books(title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int updates = statement.executeUpdate();
            if (updates < 1) {
                throw new DataProcessingException("Error during creating book " + book);
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant save book with title = " + book.getTitle(), e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant find book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(createBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant find all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int updates = statement.executeUpdate();
            if (updates < 1) {
                throw new DataProcessingException("Error during updating book " + book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update book in table", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Can not delete book by id " + id, e);
        }
    }

    private Book createBook(ResultSet resultSet ) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        return new Book(id, title, price);
    }
}
