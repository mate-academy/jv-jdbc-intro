package mate.academy.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.DbConnector;
import mate.academy.entity.Book;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;

@Dao
public class BookDaoImpl implements BookDao {
    public static final String ERROR_DURING_CREATION_OF_THE_BOOK =
            "Error during creation of the book \"%s\"";
    public static final String NO_BOOK_WITH_SUCH_ID =
            "No book with such id -> %s";
    public static final String ERROR_DURING_RETRIEVING_ALL_BOOKS =
            "Error during retrieving all books";
    public static final String SELECT_ALL_STATEMENT =
            "SELECT books.id, books.title, books.price FROM books";
    public static final String ERROR_DURING_UPDATING_BOOK =
            "Error during updating book.";
    public static final String UPDATE_BOOKS_STATEMENT =
            "UPDATE books SET books.title = ?, books.price = ? WHERE id = ?;";
    public static final String DELETE_STATEMENT =
            "DELETE FROM books WHERE books.id = ?;";
    public static final String AT_LEAST_ONE_ROW_SHOULD_BE_UPDATED =
            "At least one row should be inserted.";
    public static final String AT_LEAST_ONE_ROW_SHOULD_BE_DELETED =
            "At least one row should be deleted";
    public static final String ERROR_DURING_DELETION = "Error during deletion.";
    public static final String NO_ROWS_INSERTED = "No rows inserted.";
    private static final String CREATE_STATEMENT =
            "INSERT INTO books(title, price) VALUES(?, ?);";
    private static final String FIND_BY_ID_STATEMENT =
            "SELECT books.id, books.title, books.price FROM books WHERE books.id = ?;";
    private final DbConnector connector;

    public BookDaoImpl() {
        this.connector = new DbConnector();
    }

    @Override
    public Book create(Book book) {
        try (Connection connection = connector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_STATEMENT,
                    Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, book.getTitle());
                preparedStatement.setBigDecimal(2, book.getPrice());
                int affectedRows = preparedStatement.executeUpdate();
                if (!(affectedRows < 1)) {
                    ResultSet resultSet = preparedStatement.getGeneratedKeys();
                    if (resultSet.next()) {
                        book.setId(resultSet.getObject(1, Long.class));
                        return book;
                    }
                }
                throw new DataProcessingException(NO_ROWS_INSERTED, new RuntimeException());
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    ERROR_DURING_CREATION_OF_THE_BOOK.formatted(book.getTitle()), e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = connector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    FIND_BY_ID_STATEMENT)) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.of(mapToBook(resultSet));
                    }
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException(NO_BOOK_WITH_SUCH_ID.formatted(id), e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = connector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_ALL_STATEMENT)) {
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    List<Book> books = new LinkedList<>();
                    while (rs.next()) {
                        books.add(mapToBook(rs));
                    }
                    return books;
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException(ERROR_DURING_RETRIEVING_ALL_BOOKS, e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = connector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    UPDATE_BOOKS_STATEMENT)) {
                preparedStatement.setString(1, book.getTitle());
                preparedStatement.setBigDecimal(2, book.getPrice());
                preparedStatement.setLong(3, book.getId());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows < 1) {
                    throw new DataProcessingException(AT_LEAST_ONE_ROW_SHOULD_BE_UPDATED,
                            new RuntimeException());
                }
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessingException(ERROR_DURING_UPDATING_BOOK, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = connector.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    DELETE_STATEMENT)) {
                preparedStatement.setLong(1, id);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows < 1) {
                    throw new DataProcessingException(AT_LEAST_ONE_ROW_SHOULD_BE_DELETED,
                            new RuntimeException());
                }
                return true;
            }
        } catch (SQLException e) {
            throw new DataProcessingException(ERROR_DURING_DELETION, e);
        }
    }

    private Book mapToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }
}
