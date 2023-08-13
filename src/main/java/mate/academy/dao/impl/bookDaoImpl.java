package mate.academy.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class bookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String insertBookRequest = "INSERT INTO books(title,price) VALUES(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement createBookStatement
                     = connection.prepareStatement(insertBookRequest,
                     Statement.RETURN_GENERATED_KEYS)) {
            createBookStatement.setString(1, book.getTitle());
            createBookStatement.setBigDecimal(2, book.getPrice());
            createBookStatement.executeUpdate();
            ResultSet generatedKeys = createBookStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert book to DB", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String getBookRequest = "SELECT * FROM books "
                + "WHERE id = ? AND is_deleted = FALSE";
        Book book = new Book();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getBookStatement = connection
                     .prepareStatement(getBookRequest)) {
            getBookStatement.setLong(1, id);
            ResultSet resultSet = getBookStatement.executeQuery();
            if (resultSet.next()) {
                book = getBook(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get book from DB", e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> allBooks = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement getAllBooksStatement = connection.createStatement();) {
            ResultSet resultSet = getAllBooksStatement
                    .executeQuery("SELECT * FROM books WHERE is_deleted = FALSE ");
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getBigDecimal("price");
                Long id = resultSet.getObject("id", Long.class);
                Book book = new Book();
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                allBooks.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get all books from DB", e);
        }
        return allBooks;
    }

    @Override
    public Book update(Book book) {
        String updateBookRequest = "UPDATE books SET title = ?, price = ?"
                + " where id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement updateBookStatement
                     = connection.prepareStatement(updateBookRequest,
                     Statement.RETURN_GENERATED_KEYS)) {
            updateBookStatement.setString(1, book.getTitle());
            updateBookStatement.setBigDecimal(2, book.getPrice());
            updateBookStatement.setLong(3, book.getId());
            updateBookStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert book to DB", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteBookRequest = "UPDATE books SET is_deleted = 1"
                + " where id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteBookStatement
                     = connection.prepareStatement(deleteBookRequest,
                     Statement.RETURN_GENERATED_KEYS)) {
            deleteBookStatement.setLong(1, id);
            deleteBookStatement.executeUpdate();
            return deleteBookStatement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book from DB", e);
        }
    }

    private Book getBook(ResultSet resultSet) {
        Book book = new Book();
        try {
            Long id = resultSet.getObject("id", Long.class);
            String title = resultSet.getString("title");
            BigDecimal price = resultSet.getBigDecimal("price");
            book.setId(id);
            book.setTitle(title);
            book.setPrice(price);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book from resultSet",e);
        }
        return book;
    }
}
