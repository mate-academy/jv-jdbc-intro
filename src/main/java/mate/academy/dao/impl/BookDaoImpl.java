package mate.academy.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.connectionproperties.ConnectionProperties;
import mate.academy.dao.BookDao;
import mate.academy.entity.Book;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;

@Dao
public class BookDaoImpl implements BookDao {
    public static final String INSERT_BOOK_QUERY = "INSERT INTO books (title,price) values (?,?)";
    public static final String SELECT_FROM_BOOKS_WHERE_ID = "SELECT * FROM books WHERE id = ?";
    public static final String SELECT_FROM_BOOKS = "SELECT * FROM books";
    public static final String UPDATE_BOOKS_QUERY = "UPDATE books SET title=?, price=? where id=?";
    public static final String DELETE_FROM_BOOKS_WHERE_ID = "DELETE FROM books WHERE id = ?";
    public static final String EXPECTED_CREATE = "Expected crate at least one book, but created ";
    public static final String CAN_T_SAVE_A_BOOK_MSG = "Can't save a book ";
    public static final String CAN_T_GET_A_BOOK_BY_ID_MSG = "Can't get a book by id ";
    public static final String CAN_T_GET_A_LIST_OF_BOOK_MSG = "Can't get a list of book ";
    public static final int PARAMETER_INDEX_ONE = 1;
    public static final int PARAMETER_INDEX_TWO = 2;
    public static final int COLUMN_INDEX_THREE = 3;
    public static final int MIN_CREATE_OR_UPDATE_OR_REMOVE_BOOK = 1;
    public static final String CAN_T_DELETE_BOOK_BY_ID_MSG = "Can't delete book by ID:";
    public static final String CAN_T_UPDATE_BOOK = "Can't update book ";

    @Override
    public Book create(Book book) {
        try (PreparedStatement preparedStatement = ConnectionProperties.getConnection()
                .prepareStatement(INSERT_BOOK_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(PARAMETER_INDEX_ONE, book.getTitle());
            preparedStatement.setBigDecimal(PARAMETER_INDEX_TWO, book.getPrice());
            int executeCreate = preparedStatement.executeUpdate();
            if (executeCreate < MIN_CREATE_OR_UPDATE_OR_REMOVE_BOOK) {
                throw new RuntimeException(EXPECTED_CREATE + executeCreate);
            }
            ResultSet resultGeneratedKey = preparedStatement.getGeneratedKeys();
            while (resultGeneratedKey.next()) {
                int id = resultGeneratedKey.getObject(PARAMETER_INDEX_ONE, Integer.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CAN_T_SAVE_A_BOOK_MSG + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(int id) {
        try (PreparedStatement preparedStatement =
                     getPreparedStatement(SELECT_FROM_BOOKS_WHERE_ID)) {
            preparedStatement.setLong(PARAMETER_INDEX_ONE, id);
            ResultSet executeQuery = getExecuteQuery(preparedStatement);
            if (executeQuery.next()) {
                Book book = buildBook(executeQuery);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CAN_T_GET_A_BOOK_BY_ID_MSG + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try (PreparedStatement preparedStatement = getPreparedStatement(SELECT_FROM_BOOKS)) {
            ResultSet executeQuery = getExecuteQuery(preparedStatement);
            while (executeQuery.next()) {
                Book book = buildBook(executeQuery);
                bookList.add(book);
            }
            return bookList;
        } catch (SQLException e) {
            throw new DataProcessingException(CAN_T_GET_A_LIST_OF_BOOK_MSG, e);
        }
    }

    @Override
    public Book update(Book book) {
        try (PreparedStatement statement = getPreparedStatement(UPDATE_BOOKS_QUERY)) {
            statement.setString(PARAMETER_INDEX_ONE, book.getTitle());
            statement.setBigDecimal(PARAMETER_INDEX_TWO, book.getPrice());
            statement.setInt(COLUMN_INDEX_THREE, book.getId());
            int executeUpdate = statement.executeUpdate();
            if (executeUpdate < MIN_CREATE_OR_UPDATE_OR_REMOVE_BOOK) {
                throw new RuntimeException(EXPECTED_CREATE + executeUpdate);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CAN_T_UPDATE_BOOK + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(int id) {
        try (PreparedStatement statement = getPreparedStatement(DELETE_FROM_BOOKS_WHERE_ID)) {
            statement.setInt(PARAMETER_INDEX_ONE, id);

            int executeRemove = statement.executeUpdate();

            if (executeRemove >= MIN_CREATE_OR_UPDATE_OR_REMOVE_BOOK) {
                return true;
            }

        } catch (SQLException e) {
            throw new DataProcessingException(CAN_T_DELETE_BOOK_BY_ID_MSG + id, e);
        }
        return false;
    }

    private static ResultSet getExecuteQuery(PreparedStatement statement) throws SQLException {
        return statement.executeQuery();
    }

    private static PreparedStatement getPreparedStatement(String query) throws SQLException {
        return ConnectionProperties.getConnection()
                .prepareStatement(query);
    }

    private static Book buildBook(ResultSet executeQuery) throws SQLException {
        Book book = new Book();
        book.setId(executeQuery.getInt(PARAMETER_INDEX_ONE));
        book.setTitle(executeQuery.getString(PARAMETER_INDEX_TWO));
        book.setPrice(executeQuery.getBigDecimal(COLUMN_INDEX_THREE));
        return book;
    }
}
