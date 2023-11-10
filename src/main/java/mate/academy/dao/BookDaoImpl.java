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
import mate.academy.model.exception.AffectedRowsException;
import mate.academy.model.exception.DataProcessingException;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {

    private static final String CREATE_QUERY =
            "INSERT INTO book(title, price) VALUES(?, ?)";
    private static final String FIND_BY_ID_QUERY =
            "SELECT * FROM book WHERE id = ?";
    private static final String FIND_ALL_QUERY =
            "SELECT * FROM book";
    private static final String UPDATE_BY_ID_QUERY =
            "UPDATE book SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY =
            "DELETE FROM book WHERE id = ?";
    private static final String CREATE_OPERATION_PERFORMING_MESSAGE =
            "Can't create a book with params: ";
    private static final String AFFECTED_ROW_EXCEPTION_MESSAGE =
            "Expected to insert at least one row, but was inserted 0 row";
    private static final String CANT_FIND_BOOK_MESSAGE =
            "Can't find book by id: ";
    private static final String CANT_GET_ALL_BOOKS_MESSAGE =
            "Can't get all books from DB";
    private static final String CANT_UPDATE_BOOK_MESSAGE =
            "Can't update book with id: ";
    private static final String CANT_DELETE_BOOK_MESSAGE =
            "Can't delete book by id: ";
    private static final int FIRST_QUERY_PARAM_INDEX = 1;
    private static final int SECOND_QUERY_PARAM_INDEX = 2;
    private static final int THIRD_QUERY_PARAM_INDEX = 3;

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(FIRST_QUERY_PARAM_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_QUERY_PARAM_INDEX, book.getPrice());
            int affectedRows = statement.executeUpdate();
            checkIfAtLeastOneRowAffected(affectedRows);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CREATE_OPERATION_PERFORMING_MESSAGE
                    + book.getTitle()
                    + " "
                    + book.getPrice(), e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    FIND_BY_ID_QUERY);
            statement.setLong(FIRST_QUERY_PARAM_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = getBookFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CANT_FIND_BOOK_MESSAGE + id, e);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    FIND_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookList.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CANT_GET_ALL_BOOKS_MESSAGE, e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_QUERY);
            statement.setString(FIRST_QUERY_PARAM_INDEX, book.getTitle());
            statement.setObject(SECOND_QUERY_PARAM_INDEX, book.getPrice());
            statement.setLong(THIRD_QUERY_PARAM_INDEX, book.getId());
            int affectedRows = statement.executeUpdate();
            checkIfAtLeastOneRowAffected(affectedRows);
            Optional<Book> updatedBook = findById(book.getId());
            return updatedBook.orElse(null);
        } catch (SQLException e) {
            throw new DataProcessingException(CANT_UPDATE_BOOK_MESSAGE + book.getId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_QUERY);
            statement.setLong(FIRST_QUERY_PARAM_INDEX, id);
            int affectedRows = statement.executeUpdate();
            checkIfAtLeastOneRowAffected(affectedRows);
            // Mb, we need to return statement like this: affectedRows == 1, because,
            // it's strange, if we delete several rows with one id (but we don't have guarantee,
            // that id unique in random DB), so what?
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(CANT_DELETE_BOOK_MESSAGE + id, e);
        }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getObject("price", BigDecimal.class));
        return book;
    }

    private static void checkIfAtLeastOneRowAffected(int affectedRows) {
        if (affectedRows < 1) {
            throw new AffectedRowsException(AFFECTED_ROW_EXCEPTION_MESSAGE);
        }
    }
}
