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
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO book (title, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

            if (affectedRows < 1) {
                throw new RuntimeException(
                        "Expected to insert at least 1 row, but inserted 0 rows");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can't add new book " + e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM book WHERE id = ? ;";
        Book book;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(query);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                book = new Book();
                id = resultSet.getObject(1, Long.class);
                String title = resultSet.getString(2);
                BigDecimal price = resultSet.getBigDecimal(3);
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't find the book: " + e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM book";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                Long id = resultSet.getObject(1, Long.class);
                String title = resultSet.getString(2);
                BigDecimal price = resultSet.getBigDecimal(3);
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("Can't find all books " + e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE book "
                + "SET title = ?, price = ? "
                + "WHERE id = ? ;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new RuntimeException(
                        "Expected to update at least 1 row, but inserted 0 rows");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't update the book " + e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM BOOK WHERE id = ? ;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(query);
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 1;
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete the book " + e);
        }
    }
}
