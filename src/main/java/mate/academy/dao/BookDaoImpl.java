package mate.academy.dao;

import java.math.BigDecimal;
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

    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";

        try (PreparedStatement statement = ConnectionUtil.getConnection().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book " + book, e);
        }

        return book;
    }

    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (PreparedStatement statement = ConnectionUtil.getConnection().prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getBook(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find by id for id = " + id, e);
        }

        return Optional.empty();
    }

    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (PreparedStatement statement = ConnectionUtil.getConnection().prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                books.add(getBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't retrieve all books", e);
        }

        return books;
    }

    public Book update(Book book) {
        String sql = "UPDATE books SET price = ?, title = ? WHERE id = ?";
        try (PreparedStatement statement = ConnectionUtil.getConnection().prepareStatement(sql)) {
            statement.setBigDecimal(1, book.getPrice());
            statement.setString(2, book.getTitle());
            statement.setLong(3, book.getId());
            if (statement.executeUpdate() > 0) {
                return book;
            } else {
                throw new DataProcessingException("No book found with id = " + book.getId(),
                        new RuntimeException());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book with id = " + book.getId(), e);
        }
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement statement = ConnectionUtil.getConnection().prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id = " + id, e);
        }
    }

    private Book getBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        String title = resultSet.getString("title");
        return new Book(id, title, price);
    }
}
