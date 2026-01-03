package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.db.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO bookshelf (title, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, book.getTitle());
            preparedStatement.setObject(2, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Cannot add new book",
                        new Throwable("Exception"));
            }
            ResultSet set = preparedStatement.getGeneratedKeys();
            if (set.next()) {
                Long id = set.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot add new book", new Throwable("Exception"));
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM bookshelf WHERE id = ?;";
        Book book = new Book();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book.setTitle(resultSet.getString("title"));
                book.setId(resultSet.getObject("id", Long.class));
                book.setPrice(resultSet.getBigDecimal("price"));
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get book by ID: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM bookshelf;";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Book book = new Book();
                book.setId(set.getObject("id", Long.class));
                book.setTitle(set.getString("title"));
                book.setPrice(set.getBigDecimal("price"));
                books.add(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Cannot connect to DB", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE bookshelf SET title = ?, price = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            statement.setObject(3, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to update at least one row, "
                        + "but updated 0. Book ID: " + book.getId(),
                        new RuntimeException("Entity not found"));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM bookshelf WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id, e);
        }
    }
}
