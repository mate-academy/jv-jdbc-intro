package mate.academy.lib;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement statement =
                            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new RuntimeException(
                        "expected to insert at least one row, but instead 0 rows"
                );
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a connection to a DB", e);
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
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");

                Book book = new Book();
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a connection to a DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> list = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getObject("id", Long.class);
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");

                Book book = new Book();
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                list.add(book);
            }
            return list;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a connection to a DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET price = ? WHERE title = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBigDecimal(1, book.getPrice());
            statement.setString(2, book.getTitle());
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new RuntimeException(
                        "expected to update at least one row, but instead 0 rows"
                );
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a connection to a DB", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int updatedRows = statement.executeUpdate();
            return updatedRows > 0;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a connection to a DB", e);
        }
    }
}
