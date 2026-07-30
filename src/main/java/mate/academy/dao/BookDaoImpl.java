package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(
                        "Expected to insert at least 1 row, but inserted zero rows");
            }

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getObject(1, Long.class));
            }

            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject("id", Long.class));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));

                return Optional.of(book);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, title, price FROM books";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject("id", Long.class));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));

                books.add(book);
            }

            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException(
                        "Can't update book with id " + book.getId());
            }

            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Can't update book", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
