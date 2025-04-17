package mate.academy.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.db.DatabaseUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.model.Book;

public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO BOOKS (TITLE, PRICE) VALUES (?, ?)";
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement stmt = connection.prepareStatement(
                        sql, PreparedStatement.RETURN_GENERATED_KEYS
                )
        ) {
            stmt.setString(1, book.getTitle());
            stmt.setBigDecimal(2, book.getPrice());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                book.setId(rs.getLong(1));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert book: "
                    + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM BOOKS WHERE ID = ?";
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(parseBookFromResultSet(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by ID: "
                    + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM BOOKS";
        List<Book> books = new ArrayList<>();
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                books.add(parseBookFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't retrieve books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE BOOKS SET TITLE = ?, PRICE = ? WHERE ID = ?";
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)
        ) {
            stmt.setString(1, book.getTitle());
            stmt.setBigDecimal(2, book.getPrice());
            stmt.setLong(3, book.getId());
            stmt.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book: "
                    + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM BOOKS WHERE ID = ?";
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with ID: "
                    + id, e);
        }
    }

    private Book parseBookFromResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getObject("ID", Long.class);
        String title = rs.getString("TITLE");
        BigDecimal price = rs.getBigDecimal("PRICE");
        return new Book(id, title, price);
    }
}
