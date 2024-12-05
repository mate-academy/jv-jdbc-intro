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
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.service.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sqlInsert = "INSERT INTO books (title, price) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sqlInsert, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row,"
                        + " but inserted 0 rows.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to insert book into the database: "
                    + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlSelect = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlSelect)) {

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
            throw new DataProcessingException("Unable to retrieve book with id: " + id, e);
        }
        return null;
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        String sqlSelectAll = "SELECT * from books";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlSelectAll)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getBigDecimal("price")
                );
                bookList.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to retrieve all books from the database", e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        String sqlUpdate = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlUpdate)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setInt(3, Math.toIntExact(book.getId()));

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row,"
                        + " but inserted 0 rows.");
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update book with id: " + book.getId(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlDelete = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlDelete)) {

            statement.setLong(1, id);

            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete book with id: " + id, e);
        }
        return false;
    }
}
