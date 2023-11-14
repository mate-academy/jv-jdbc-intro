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
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.service.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String CAN_NOT_FIND_BOOK_BY_ID_EXCEPTION
            = "Can't find the book by this id!";
    private static final String CAN_NOT_INSERT_BOOK_EXCEPTION
            = "Can't insert the book into DB!";
    private static final String CAN_NOT_FIND_ALL_BOOKS_EXCEPTION
            = "Can't find all books!";
    private static final String CAN_NOT_UPDATE_BOOK_EXCEPTION
            = "Can't update this book!";
    private static final String CAN_NOT_DELETE_BY_ID_EXCEPTION
            = "Can't delete the book by this id!";
    private static final String CREATE_SQL_QUERY
            = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String UPDATE_SQL_QUERY
            = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String FIND_BY_ID_SQL_QUERY
            = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_SQL_QUERY
            = "SELECT * FROM books";
    private static final String DELETE_SQL_QUERY
            = "DELETE FROM books WHERE id = ?";
    private static final int FIRST_PARAMETER_INDEX = 1;
    private static final int SECOND_PARAMETER_INDEX = 2;
    private static final int THIRD_PARAMETER_INDEX = 3;
    private static final int FIRST_COLUMN_INDEX = 1;
    private static final String INSERT_ACTION = "insert";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                CREATE_SQL_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(FIRST_PARAMETER_INDEX, book.getTitle());
            preparedStatement.setObject(SECOND_PARAMETER_INDEX, book.getPrice());

            int affectedRows = preparedStatement.executeUpdate();
            validateAffectedRows(affectedRows, INSERT_ACTION);

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(FIRST_COLUMN_INDEX, Long.class));
            }

        } catch (SQLException e) {
            throw new DataProcessingException(CAN_NOT_INSERT_BOOK_EXCEPTION, e);
        }
        return book;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(UPDATE_SQL_QUERY)) {

            preparedStatement.setString(FIRST_PARAMETER_INDEX, book.getTitle());
            preparedStatement.setObject(SECOND_PARAMETER_INDEX, book.getPrice());
            preparedStatement.setLong(THIRD_PARAMETER_INDEX, book.getId());

            int affectedRows = preparedStatement.executeUpdate();
            validateAffectedRows(affectedRows, UPDATE_ACTION);

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book = extractBook(generatedKeys);
            }

        } catch (SQLException e) {
            throw new DataProcessingException(CAN_NOT_UPDATE_BOOK_EXCEPTION, e);
        }

        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(FIND_BY_ID_SQL_QUERY)) {

            preparedStatement.setLong(FIRST_PARAMETER_INDEX, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                book = extractBook(resultSet);
            }

        } catch (SQLException e) {
            throw new DataProcessingException(CAN_NOT_FIND_BOOK_BY_ID_EXCEPTION, e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                Statement statement =
                        connection.prepareStatement(FIND_ALL_SQL_QUERY)) {

            ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL_QUERY);
            while (resultSet.next()) {
                list.add(extractBook(resultSet));
            }

        } catch (SQLException e) {
            throw new DataProcessingException(CAN_NOT_FIND_ALL_BOOKS_EXCEPTION, e);
        }
        return list;
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(DELETE_SQL_QUERY)) {

            preparedStatement.setLong(FIRST_PARAMETER_INDEX, id);
            int affectedRows = preparedStatement.executeUpdate();
            validateAffectedRows(affectedRows, DELETE_ACTION);
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new DataProcessingException(CAN_NOT_DELETE_BY_ID_EXCEPTION, e);
        }
    }

    private Book extractBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getObject("price", BigDecimal.class));
        return book;
    }

    private static void validateAffectedRows(int affectedRows, String action) {
        if (affectedRows < 1) {
            throw new RuntimeException("Expected to " + action + " at least one row.");
        }
    }
}
