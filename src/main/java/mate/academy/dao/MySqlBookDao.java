package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.DataProcessingException;
import mate.academy.util.MySqlConnectionUtil;

@Dao
public class MySqlBookDao implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";

        try (Connection connection = MySqlConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setObject(1, book.title());
            statement.setObject(2, book.price());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    return book.withId(resultSet.getObject(1, Long.class));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot insert a book: " + book, e);
        }

        throw new DataProcessingException("Cannot insert a book: " + book);
    }

    @Override
    public Optional<Book> findById(long id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = MySqlConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setObject(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapToBook(resultSet));
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find a book by id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();

        try (Connection connection = MySqlConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(mapToBook(resultSet));
            }

            return books;

        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get all books.", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = MySqlConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setObject(1, book.title());
            statement.setObject(2, book.price());
            statement.setObject(3, book.id());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update a book: " + book, e);
        }
        throw new DataProcessingException("Cannot update a book: " + book);
    }

    @Override
    public boolean deleteById(long id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection connection = MySqlConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setObject(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete a book by id " + id, e);
        }
    }

    private Book mapToBook(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getObject("id", Long.class),
                resultSet.getObject("title", String.class),
                resultSet.getObject("price", BigDecimal.class)
        );
    }
}
