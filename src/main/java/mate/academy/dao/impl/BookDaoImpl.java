package mate.academy.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        query,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException(
                        "Creating book failed, no rows affected");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't insert book into DB", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setTitle(rs.getString("title"));
                book.setPrice(rs.getBigDecimal("price"));
                return Optional.of(book);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't find book by id" + id, e);
        }
    }
    ;

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setTitle(rs.getString("title"));
                book.setPrice(rs.getBigDecimal("price"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't get all books", e);
        }
    }
    ;

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataProcessingException(
                        "Can't update book, id not found: " + book.getId(),
                        null);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't update book", e);
        }
    }
    ;

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id " + id, e);
        }
    }
}
