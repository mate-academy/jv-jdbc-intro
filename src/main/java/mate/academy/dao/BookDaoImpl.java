package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    private static final String DB_USER = "username";
    private static final String DB_PASSWORD = "password";

    // SQL Queries
    private static final String INSERT_BOOK =
            "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String SELECT_BY_ID =
            "SELECT * FROM books WHERE id = ?";
    private static final String SELECT_ALL =
            "SELECT * FROM books";
    private static final String UPDATE_BOOK =
            "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BY_ID =
            "DELETE FROM books WHERE id = ?";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    @Override
    public Book create(Book book) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_BOOK, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, book.getTitle());
            stmt.setBigDecimal(2, book.getPrice());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }

            return book;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book: " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToBook(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();

        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_ALL)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(mapResultSetToBook(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }

        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_BOOK)) {

            stmt.setString(1, book.getTitle());
            stmt.setBigDecimal(2, book.getPrice());
            stmt.setLong(3, book.getId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating book failed, no rows affected.");
            }

            return book;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_BY_ID)) {

            stmt.setLong(1, id);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id, e);
        }
    }

    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Long id = rs.getObject("id", Long.class);
        String title = rs.getString("title");
        BigDecimal price = rs.getBigDecimal("price");
        return new Book(id, title, price);
    }
}
