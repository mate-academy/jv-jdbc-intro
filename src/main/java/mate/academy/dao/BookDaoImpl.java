package mate.academy.dao;

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
        String query = "INSERT INTO books (title, price) VALUES (?, ?);";

        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement ps = connection.prepareStatement(
                         query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, book.getTitle());
            ps.setBigDecimal(2, book.getPrice());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    book.setId(rs.getObject(1, Long.class));
                }
            }
            return book;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book: " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?;";

        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapToBook(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book id=" + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books;";
        List<Book> books = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                books.add(mapToBook(rs));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books", e);
        }

        return books;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?;";

        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, book.getTitle());
            ps.setBigDecimal(2, book.getPrice());
            ps.setLong(3, book.getId());
            ps.executeUpdate();

            return book;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?;";

        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book id=" + id, e);
        }
    }

    private Book mapToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getObject("id", Long.class));
        book.setTitle(rs.getString("title"));
        book.setPrice(rs.getBigDecimal("price"));
        return book;
    }
}
