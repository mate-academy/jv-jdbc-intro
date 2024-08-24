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

@Dao
public class BookDaoimpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book(title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql,
                         Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, book.getTitle());
            ps.setBigDecimal(2, book.getPrice());

            int affectedRows = ps.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row, but inserted "
                        + affectedRows + " rows");
            }
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                Long id = rs.getLong(1);
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
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Book book = getBookFromResultSet(rs);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a connection to the DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = getBookFromResultSet(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find any books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE book SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setBigDecimal(2, book.getPrice());
            ps.setLong(3, book.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Failed to update the book with id: " + book.getId());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update book", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Failed to delete the book with id: " + id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete book", e);
        }
        return true;
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
