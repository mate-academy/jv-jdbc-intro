package mate.academy.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.DataProcessingException;
import mate.academy.dao.BookDao;
import mate.academy.dao.ConnectionUtil;
import mate.academy.lib.Dao;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Expected at least 1 book " + book);
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    book.setId(id);
                } else {
                    throw new DataProcessingException("Expected id to be present");
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Book book = new Book();
                    book.setTitle(resultSet.getString("title"));
                    book.setPrice(resultSet.getBigDecimal("price"));
                    book.setId(resultSet.getObject("id", Long.class));

                    return Optional.of(book);
                }
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can`t find book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            List<Book> books;
            try (ResultSet resultSet = statement.executeQuery()) {
                books = new ArrayList<>();

                while (resultSet.next()) {
                    Book book = new Book();
                    book.setTitle(resultSet.getString("title"));
                    book.setPrice(resultSet.getBigDecimal("price"));
                    book.setId(resultSet.getObject("id", Long.class));
                    books.add(book);
                }
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books ", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DataProcessingException("Can't update book " + book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book ", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return true;
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id " + id, e);
        }
        return false;
    }
}
