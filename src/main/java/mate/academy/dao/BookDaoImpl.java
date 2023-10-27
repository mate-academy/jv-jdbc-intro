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
import mate.academy.ConnectionUtil;
import mate.academy.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

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
            checkAffectedRows(preparedStatement.executeUpdate());
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getObject(ONE, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CANNOT_CREATE, e);
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
                return Optional.of(mapingOfBook(resultSet.getObject(ID, Long.class),
                       resultSet.getString(TITLE), resultSet.getObject(PRICE, BigDecimal.class)));
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
                Book book = mapingOfBook(resultSet.getObject(ID, Long.class),
                        resultSet.getString(TITLE), resultSet.getObject(PRICE, BigDecimal.class));
                listOfBooks.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get List of books", e);
        }
        return listOfBooks;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement
                        = connection.prepareStatement(sql)) {
            preparedStatement.setString(ONE, book.getTitle());
            preparedStatement.setBigDecimal(TWO, book.getPrice());
            preparedStatement.setLong(THREE, book.getId());
            checkAffectedRows(preparedStatement.executeUpdate());
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
            preparedStatement.setLong(ONE, id);
            return preparedStatement.executeUpdate() >= ONE;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete book by id:" + id, e);
        }
    }

    private static void checkAffectedRows(int affectedRows) {
        if (affectedRows < ONE) {
            throw new RuntimeException(FEW_AFFECTED_ROWS);
        }
    }

    private static Book mapingOfBook(Long id, String title, BigDecimal price) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
