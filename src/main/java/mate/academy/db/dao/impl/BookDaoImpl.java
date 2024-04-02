package mate.academy.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.db.ConnectionUtil;
import mate.academy.db.DataProcessingException;
import mate.academy.db.dao.BookDao;
import mate.academy.db.dao.SqlQueries;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        try (Connection conn = ConnectionUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SqlQueries.CREATE_BOOK,
                        Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setBigDecimal(2, book.getPrice());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least 1 row, but was 0 rows.");
            }
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add new book to DB: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection conn = ConnectionUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SqlQueries.GET_BOOK_BY_ID)) {
            pstmt.setLong(1, id);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                return Optional.of(getBook(rset));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book from DB with id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Connection conn = ConnectionUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SqlQueries.GET_ALL_BOOKS)) {
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                books.add(getBook(rset));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get books list from DB.", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        try (Connection conn = ConnectionUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SqlQueries.UPDATE_BOOK)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setBigDecimal(2, book.getPrice());
            pstmt.setLong(3, book.getId());
            int updatedRows = pstmt.executeUpdate();
            if (updatedRows < 1) {
                throw new RuntimeException("Expected to update at least 1 row, but was 0 rows.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book in DB: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection conn = ConnectionUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(SqlQueries.DELETE_BOOK)) {
            pstmt.setLong(1, id);
            int deletedRows = pstmt.executeUpdate();
            return deletedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book from DB with id: " + id, e);
        }
    }

    private static Book getBook(ResultSet rset) throws SQLException {
        Book book = new Book();
        book.setId(rset.getObject("id", Long.class));
        book.setTitle(rset.getString("title"));
        book.setPrice(rset.getBigDecimal("price"));
        return book;
    }
}
