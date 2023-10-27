package mate.academy.dao;

import mate.academy.ConnectionUtil;
import mate.academy.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String ID = "id";
    private static final String PRICE = "price";
    private static final String TITLE = "title";
    private static final String CANNOT_CREATE = "Cannot add new book to books";
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final String FEW_AFFECTED_ROWS = "Affected rows should be more than 0";
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(ONE, book.getTitle());
            preparedStatement.setBigDecimal(TWO, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < ONE) {
                throw new RuntimeException(FEW_AFFECTED_ROWS);
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long bookId = resultSet.getObject(ONE, Long.class);
                book.setId(bookId);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CANNOT_CREATE , e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
           preparedStatement.setLong(ONE, id);
           ResultSet resultSet = preparedStatement.executeQuery();
           if (resultSet.next()) {
               Long bookId = resultSet.getObject(ID, Long.class);
               String title = resultSet.getString(TITLE);
               BigDecimal bigDecimal
                       = resultSet.getObject(PRICE, BigDecimal.class);
               Book book = new Book();
               book.setId(bookId);
               book.setTitle(title);
               book.setPrice(bigDecimal);
               return Optional.of(book);
           }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> listOfBooks = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long bookId = resultSet.getObject(ID, Long.class);
                String title = resultSet.getString(TITLE);
                BigDecimal bigDecimal = resultSet.getObject(PRICE, BigDecimal.class);
                Book book = new Book();
                book.setId(bookId);
                book.setTitle(title);
                book.setPrice(bigDecimal);
                listOfBooks.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get List of books ", e);
        }
        return listOfBooks;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET ? = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement preparedStatement
                = connection.prepareStatement(sql)) {
            preparedStatement.setString(ONE, TITLE);
            preparedStatement.setString(TWO, book.getTitle());
            preparedStatement.setLong(THREE, book.getId());
            int affectedRowsFirstTime = preparedStatement.executeUpdate();
            preparedStatement.setString(ONE, PRICE);
            preparedStatement.setBigDecimal(TWO, book.getPrice());
            preparedStatement.setLong(THREE, book.getId());
            int affectedRows = affectedRowsFirstTime
                    + preparedStatement.executeUpdate();
            if (affectedRows < ONE) {
                throw new RuntimeException(FEW_AFFECTED_ROWS);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot update book " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete book by id:" + id, e);
        }
    }
}
