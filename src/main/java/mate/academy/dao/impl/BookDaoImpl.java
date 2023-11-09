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
import mate.academy.utils.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String PROCESSING_FAILURE_MESSAGE = "Processing Data Failure!";
    private static final String ID_COLUMN_LABEL = "id";
    private static final String TITLE_COLUMN_LABEL = "title";
    private static final String PRICE_COLUMN_LABEL = "price";
    private static final String CREATE_SQL_OPERATION =
            "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String FIND_SQL_OPERATION =
            "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_SQL_OPERATION =
            "SELECT * FROM books";
    private static final String UPDATE_SQL_OPERATION =
            "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_SQL_OPERATION =
            "DELETE FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (PreparedStatement preparedStatement = ConnectionUtil
                             .getConnection()
                             .prepareStatement(CREATE_SQL_OPERATION,
                                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setObject(2, book.getPrice());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
            return book;
        } catch (SQLException ex) {
            throw new DataProcessingException(PROCESSING_FAILURE_MESSAGE
                    + " Book: " + book, ex);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (PreparedStatement preparedStatement =
                     ConnectionUtil.getConnection().prepareStatement(FIND_SQL_OPERATION)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Book book = new Book();
            if (resultSet.next()) {
                String title = resultSet.getString(TITLE_COLUMN_LABEL);
                BigDecimal price = resultSet.getBigDecimal(PRICE_COLUMN_LABEL);
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
            }
            return Optional.of(book);
        } catch (SQLException ex) {
            throw new DataProcessingException(PROCESSING_FAILURE_MESSAGE
                    + " ID: " + id, ex);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();
        try (PreparedStatement preparedStatement =
                     ConnectionUtil.getConnection().prepareStatement(FIND_ALL_SQL_OPERATION)) {
            ResultSet resultSet = preparedStatement.executeQuery(FIND_ALL_SQL_OPERATION);
            while (resultSet.next()) {
                list.add(getBook(resultSet));
            }
            return list;
        } catch (SQLException ex) {
            throw new DataProcessingException(PROCESSING_FAILURE_MESSAGE, ex);
        }
    }

    @Override
    public Book update(Book book) {
        try (PreparedStatement statement =
                     ConnectionUtil.getConnection().prepareStatement(UPDATE_SQL_OPERATION)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            statement.executeUpdate();
            return findById(book.getId()).orElse(null);
        } catch (SQLException ex) {
            throw new DataProcessingException(PROCESSING_FAILURE_MESSAGE
                    + " Book: " + book, ex);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_SQL_OPERATION)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(PROCESSING_FAILURE_MESSAGE
                    + " ID: " + id, e);
        }
    }

    private Book getBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject(ID_COLUMN_LABEL, Long.class));
        book.setTitle(resultSet.getString(TITLE_COLUMN_LABEL));
        book.setPrice(resultSet.getObject(PRICE_COLUMN_LABEL, BigDecimal.class));
        return book;
    }
}
