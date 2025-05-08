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
import mate.academy.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String INSERT = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String FIND = "SELECT * FROM books WHERE id = ?";
    private static final String UPDATE = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM books WHERE id = ?";
    private static final String ID = "id";
    private static final String PRICE = "price";
    private static final String TITLE = "title";
    private static final String CANNOT_CREATE = "Cannot add new book to books";
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final String FEW_AFFECTED_ROWS = "Affected rows should be more than 0";
    private static final String SELECT_SQL = "SELECT * FROM books";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement
                        = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
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
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND)) {
            preparedStatement.setLong(ONE, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapToBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> listOfBooks = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = mapToBook(resultSet);
                listOfBooks.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get List of books", e);
        }
        return listOfBooks;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement
                        = connection.prepareStatement(UPDATE)) {
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
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
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

    private static Book mapToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject(ID, Long.class));
        book.setTitle(resultSet.getString(TITLE));
        book.setPrice(resultSet.getObject(PRICE, BigDecimal.class));
        return book;
    }
}
