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
    private static final String ID_COLUMN_NAME = "id";
    private static final String TITLE_COLUMN_NAME = "title";
    private static final String PRICE_COLUMN_NAME = "price";

    @Override
    public Book create(Book book) {
        String insertBookQuery = "INSERT INTO books (title, price) VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createFormatStatement =
                        connection.prepareStatement(insertBookQuery,
                                Statement.RETURN_GENERATED_KEYS)) {
            createFormatStatement.setString(1, book.getTitle());
            createFormatStatement.setString(2, String.valueOf(book.getPrice()));
            createFormatStatement.executeUpdate();
            ResultSet generatedKeys = createFormatStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert book to db", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String getBookByIdQuery = "SELECT * FROM books WHERE id = ?";
        Optional<Book> receivedBookOptional = Optional.empty();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getBookByIdStatement =
                        connection.prepareStatement(getBookByIdQuery)) {
            getBookByIdStatement.setObject(1, id);
            ResultSet resultSet = getBookByIdStatement.executeQuery();
            while (resultSet.next()) {
                receivedBookOptional = Optional.of(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book by id: " + id + " from db", e);
        }
        return receivedBookOptional;
    }

    @Override
    public List<Book> findAll() {
        String getBooksQuery = "SELECT * FROM books";
        List<Book> allBooks = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllFormatsStatement =
                        connection.prepareStatement(getBooksQuery)) {
            ResultSet resultSet = getAllFormatsStatement.executeQuery();
            while (resultSet.next()) {
                allBooks.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all books from db", e);
        }
        return allBooks;
    }

    @Override
    public Book update(Book book) {
        String deleteBookQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateBookStatement =
                        connection.prepareStatement(deleteBookQuery)) {
            updateBookStatement.setString(1, book.getTitle());
            updateBookStatement.setString(2, book.getPrice().toString());
            updateBookStatement.setObject(3, book.getId());
            updateBookStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book by id: " + book.getId(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteBookQuery = "DELETE FROM books WHERE id = ?";
        int changedRows;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteBookStatement =
                        connection.prepareStatement(deleteBookQuery)) {
            deleteBookStatement.setObject(1, id);
            changedRows = deleteBookStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id, e);
        }
        return changedRows > 0;
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject(ID_COLUMN_NAME, Long.class));
        book.setTitle(resultSet.getString(TITLE_COLUMN_NAME));
        book.setPrice(resultSet.getObject(PRICE_COLUMN_NAME, BigDecimal.class));
        return book;
    }
}
