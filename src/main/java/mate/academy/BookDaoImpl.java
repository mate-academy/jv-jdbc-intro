package mate.academy;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.BookDao;
import mate.academy.lib.Dao;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO Books (id, title, price) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, book.getId());
            statement.setString(2, book.getTitle());
            statement.setBigDecimal(3, book.getPrice());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException(
                        "Expected to insert at least one row, but inserted 0 rows");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getObject(1, Long.class);
                    book.setId(id);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can't add new Book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT id, title, price FROM Books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String title = Optional.ofNullable(resultSet.getString("title")).orElse("");
                    BigDecimal price = resultSet.getBigDecimal("price");
                    return Optional.of(new Book(id, title, price));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while finding Book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, title, price FROM Books";
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                books.add(new Book(id, title, price));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while finding all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book updatedBook) {
        String sql = "UPDATE Books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, updatedBook.getTitle());
            statement.setBigDecimal(2, updatedBook.getPrice());
            statement.setLong(3, updatedBook.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException("No book found with id: " + updatedBook.getId());
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while updating Book: " + updatedBook, e);
        }
        return updatedBook;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM Books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting Book by id: " + id, e);
        }
    }
}
