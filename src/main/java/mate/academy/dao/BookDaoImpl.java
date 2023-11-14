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
    private static final String ZERO_INSERTED_ROWS_MESSAGE
            = "Expected to insert at least 1 row, but inserted 0 rows";
    private static final String CANNOT_SAVE_BOOK_MESSAGE = "Cannot save this book: ";
    private static final String CANNOT_FIND_BOOK_BY_ID_MESSAGE = "Cannot find a book by this id: ";
    private static final String CANNOT_FIND_BOOKS_MESSAGE = "Cannot find books from the table";
    private static final String ZERO_UPDATED_BOOKS_MESSAGE = "No books were updated in the table";
    private static final String CANNOT_UPDATE_BOOK_MESSAGE = "Cannot update this book: ";
    private static final String CANNOT_DELETE_BOOK_BY_ID_MESSAGE
            = "Cannot delete a book by this id: ";

    @Override
    public Book create(Book book) {
        String sql = SAVE_QUERY;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(ZERO_INSERTED_ROWS_MESSAGE);
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CANNOT_SAVE_BOOK_MESSAGE + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = FIND_BY_ID_QUERY;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);

                Book book = new Book(id, title, price);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(CANNOT_FIND_BOOK_BY_ID_MESSAGE + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = FIND_ALL_QUERY;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> bookList = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                bookList.add(new Book(id, title, price));
            }
            return bookList;
        } catch (SQLException e) {
            throw new DataProcessingException(CANNOT_FIND_BOOKS_MESSAGE, e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = UPDATE_QUERY;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException(ZERO_UPDATED_BOOKS_MESSAGE);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(CANNOT_UPDATE_BOOK_MESSAGE + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = DELETE_BY_ID_QUERY;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException(CANNOT_DELETE_BOOK_BY_ID_MESSAGE + id, e);
        }
    }
}
