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
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "Insert INTO books (title, price) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                            connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least 1 row.");
            }

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                Long id = rs.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert book into DB: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "Select * FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Book book = new Book();
                Long bookId = rs.getLong("id");
                String bookName = rs.getString("title");
                BigDecimal bookPrice = rs.getBigDecimal("price");
                book.setId(bookId);
                book.setTitle(bookName);
                book.setPrice(bookPrice);

                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get book from DB.", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement pr = connection.prepareStatement(sql)) {

            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                Long bookId = rs.getLong("id");
                String bookName = rs.getString("title");
                BigDecimal bookPrice = rs.getBigDecimal("price");
                book.setId(bookId);
                book.setTitle(bookName);
                book.setPrice(bookPrice);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all books from DB!", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement pr = connection.prepareStatement(sql)) {

            pr.setString(1, book.getTitle());
            pr.setBigDecimal(2, book.getPrice());
            pr.setLong(3, book.getId());

            int affectedRows = pr.executeUpdate();

            if (affectedRows > 0) {
                return book;
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update book in DB: " + book, e);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement pr = connection.prepareStatement(sql)) {

            pr.setLong(1, id);

            int affectedRows = pr.executeUpdate();

            if (affectedRows < 1) {
                return false;
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete book from DB!", e);
        }
        return true;
    }
}
