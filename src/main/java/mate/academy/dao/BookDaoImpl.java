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
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String SAVE_QUERY = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM books";
    private static final String UPDATE_QUERY = "UPDATE books SET title = ?, price = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM books WHERE id = ?";
    private static final int FIRST_PARAMETER_INDEX = 1;
    private static final int SECOND_PARAMETER_INDEX = 2;
    private static final int THIRD_PARAMETER_INDEX = 3;
    private static final String ID_COLUMN_LABEL = "id";
    private static final String TITLE_COLUMN_LABEL = "title";
    private static final String PRICE_COLUMN_LABEL = "price";
    private static final String ZERO_INSERTED_ROWS_MESSAGE
            = "Expected to insert at least 1 row, but inserted 0 rows";
    private static final String CANNOT_SAVE_BOOK_MESSAGE_TEMPLATE = "Cannot save this book: ";
    private static final String CANNOT_FIND_BOOK_BY_ID_MESSAGE_TEMPLATE = "Cannot find a book by this id: ";
    private static final String CANNOT_FIND_BOOKS_MESSAGE = "Cannot find books from the table";
    private static final String ZERO_UPDATED_BOOKS_MESSAGE = "No books were updated in the table";
    private static final String CANNOT_UPDATE_BOOK_MESSAGE_TEMPLATE = "Cannot update this book: ";
    private static final String CANNOT_DELETE_BOOK_BY_ID_MESSAGE_TEMPLATE
            = "Cannot delete a book by this id: ";

    @Override
    public Book create(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_PARAMETER_INDEX, book.getPrice());
            if (statement.executeUpdate() < 1) {
                throw new DataProcessingException(ZERO_INSERTED_ROWS_MESSAGE);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_COLUMN_LABEL, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CANNOT_SAVE_BOOK_MESSAGE_TEMPLATE + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(FIRST_PARAMETER_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? Optional.of(mapResultSetToBook(resultSet)) : Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException(CANNOT_FIND_BOOK_BY_ID_MESSAGE_TEMPLATE + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> bookList = new ArrayList<>();
            while (resultSet.next()) {
                bookList.add(mapResultSetToBook(resultSet));
            }
            return bookList;
        } catch (SQLException e) {
            throw new DataProcessingException(CANNOT_FIND_BOOKS_MESSAGE, e);
        }
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(FIRST_PARAMETER_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_PARAMETER_INDEX, book.getPrice());
            statement.setLong(THIRD_PARAMETER_INDEX, book.getId());
            if (statement.executeUpdate() < 1) {
                throw new RuntimeException(ZERO_UPDATED_BOOKS_MESSAGE);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(CANNOT_UPDATE_BOOK_MESSAGE_TEMPLATE + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {
            statement.setLong(FIRST_PARAMETER_INDEX, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(CANNOT_DELETE_BOOK_BY_ID_MESSAGE_TEMPLATE + id, e);
        }
    }

    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject(ID_COLUMN_LABEL, Long.class);
        String title = resultSet.getString(TITLE_COLUMN_LABEL);
        BigDecimal price = resultSet.getBigDecimal(PRICE_COLUMN_LABEL);
        return new Book(id, title, price);
    }
}
