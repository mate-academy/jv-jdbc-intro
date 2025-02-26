package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.Book;

public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                       Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert, but inserted 0 rows.");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
               PreparedStatement statement = connection.prepareStatement(sql,
                       Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getBigDecimal("price"));
                return Optional.of(book);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book with id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        try (Connection connection = ConnectionUtil.getConnection();
               PreparedStatement statement = connection.prepareStatement(sql,
                       Statement.RETURN_GENERATED_KEYS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                Book book = create_book(resultSet);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get books from db: ", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE book SET title = ?, price = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
               PreparedStatement statement = connection.prepareStatement(sql,
                       Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert, but inserted 0 rows.");
            } else {
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book with id: " + book.getId(),  e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
               PreparedStatement statement = connection.prepareStatement(sql,
                       Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            int deleted_rows = statement.executeUpdate();
            return deleted_rows > 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id, e);
        }
    }

    private Book create_book(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }
}
