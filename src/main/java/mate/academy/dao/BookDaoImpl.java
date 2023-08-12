package mate.academy.dao;

import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;
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
        String query = "INSERT INTO books(title, price) VALUES(?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            int effectedRows = statement.executeUpdate();

            if (effectedRows != 1) {
                throw new DataProcessingException("Cannot insert 1 row: " + book);
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create connection to the BD", e);
        }

        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(getBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create connection to the BD", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        List<Book> result = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                result.add(getBook(resultSet));
            }

            return result;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create connection to the BD", e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title=?, price=? WHERE id=?";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            statement.setObject(3, book.getId());

            int effectedRows = statement.executeUpdate();
            if (effectedRows != 1) {
                throw new DataProcessingException("Cannot update a book with id:" + book.getId());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create connection to the BD", e);
        }

        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            int effectedRows = statement.executeUpdate();
            if (effectedRows != 1) {
                throw new DataProcessingException("Cannot delete book with id:" + id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create connection to the BD", e);
        }

        return true;
    }

    private Book getBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        Integer price = resultSet.getObject("price", Integer.class);

        return new Book(id, title, BigDecimal.valueOf(price));
    }
}
