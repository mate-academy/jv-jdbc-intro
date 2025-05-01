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
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    public static final int TITLE_INDEX = 1;
    public static final int PRICE_INDEX = 2;
    public static final int KEY_INDEX = 1;
    public static final int KEY_POSITION_INDEX = 3;
    public static final int MINIMUM_AFFECTED_ROWS = 1;
    public static final int EXCEPTION_AFFECTED_ROWS = 0;
    private static final String ID_COLUMN = "id";
    private static final String TITLE_COLUMN = "title";
    private static final String PRICE_COLUMN = "price";

    @Override
    public Book create(Book book) {
        String createQuery = "INSERT INTO book(title,price) VALUE(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX,book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < MINIMUM_AFFECTED_ROWS) {
                throw new DataProcessingException("Expected to insert at least one row,"
                        + " but inserted 0 rows",null);
            }
            ResultSet generateKeys = statement.getGeneratedKeys();
            if (generateKeys.next()) {
                long id = generateKeys.getObject(KEY_INDEX,long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String findByIdQuery = "SELECT * FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(findByIdQuery)) {
            statement.setLong(KEY_INDEX,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String findAllQuery = "SELECT * FROM book";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(findAllQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(parseBookFromResultSet(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to select all books form DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        String updateQuery = "UPDATE book SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX, book.getPrice());
            statement.setLong(KEY_POSITION_INDEX, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == EXCEPTION_AFFECTED_ROWS) {
                throw new RuntimeException("Updating book failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Error updating book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteQuery = "DELETE FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(KEY_INDEX, id);
            int updatedRows = statement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Error deleting book by id: " + id, e);
        }
    }

    private Book parseBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book newBook = new Book();
        newBook.setId(resultSet.getObject(ID_COLUMN, Long.class));
        newBook.setTitle(resultSet.getObject(TITLE_COLUMN, String.class));
        newBook.setPrice(resultSet.getObject(PRICE_COLUMN, BigDecimal.class));
        return newBook;
    }
}
