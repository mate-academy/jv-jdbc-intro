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
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int FIRST_PARAMETER_INDEX = 1;
    private static final int SECOND_PARAMETER_INDEX = 2;
    private static final int THIRD_PARAMETER_INDEX = 3;
    private static final int FIRST_COLUMN_INDEX = 1;
    private static final int SECOND_COLUMN_INDEX = 2;
    private static final int THIRD_COLUMN_INDEX = 3;
    private static final String SQL_CREATE_REQUEST = "INSERT INTO "
            + "books (title, price) VALUES (?, ?)";
    private static final String SQL_UPDATE_REQUEST = "UPDATE books"
            + " SET title = ?, price = ? WHERE id = ?";
    private static final String SQL_DELETE_REQUEST = "DELETE FROM"
            + " books WHERE id = ?";
    private static final String SQL_OUTPUT_REQUEST = "SELECT * "
            + "FROM books";
    private static final String SQL_BY_ID_REQUEST = "SELECT * "
            + "FROM books WHERE id = ?";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                           .prepareStatement(SQL_CREATE_REQUEST, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(FIRST_PARAMETER_INDEX, book.getTitle());
            preparedStatement.setBigDecimal(SECOND_PARAMETER_INDEX, book.getPrice());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException("Expected to "
                        + "inserted at least one row, but inserted 0 rows.");
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(FIRST_COLUMN_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to establish connection"
                    + " with the database", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Optional<Book> optionalBook = Optional.empty();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                           .prepareStatement(SQL_BY_ID_REQUEST)) {
            preparedStatement.setLong(FIRST_PARAMETER_INDEX, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long identify = resultSet.getObject(FIRST_COLUMN_INDEX, Long.class);
                String title = resultSet.getObject(SECOND_COLUMN_INDEX, String.class);
                BigDecimal price = resultSet.getObject(THIRD_COLUMN_INDEX, BigDecimal.class);

                Book book = new Book(identify, title, price);
                optionalBook = Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to establish connection"
                    + " with the database", e);
        }
        return optionalBook;
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(SQL_OUTPUT_REQUEST)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getObject(FIRST_COLUMN_INDEX, Long.class);
                String title = resultSet.getObject(SECOND_COLUMN_INDEX, String.class);
                BigDecimal price = resultSet.getObject(THIRD_COLUMN_INDEX, BigDecimal.class);

                Book book = new Book(id, title, price);
                bookList.add(book);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Failed to establish connection"
                    + " with the database", e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(SQL_UPDATE_REQUEST)) {
            preparedStatement.setString(FIRST_PARAMETER_INDEX, book.getTitle());
            preparedStatement.setBigDecimal(SECOND_PARAMETER_INDEX, book.getPrice());
            preparedStatement.setLong(THIRD_PARAMETER_INDEX, book.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows < 1) {
                throw new DataProcessingException("No book found with ID: " + book.getId());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to establish connection"
                    + " with the database", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        int affectedRows = 0;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(SQL_DELETE_REQUEST)) {
            preparedStatement.setLong(FIRST_PARAMETER_INDEX, id);
            affectedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to establish "
                    + "connection with the database", e);
        }
        return affectedRows > 0;
    }
}
