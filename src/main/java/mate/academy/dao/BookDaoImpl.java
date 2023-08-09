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
import mate.academy.services.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int FIRST_INDEX_PARAMETER = 1;
    private static final int SECOND_INDEX_PARAMETER = 2;
    private static final int THIRD_INDEX_PARAMETER = 3;
    private static final int ZERO_ROWS = 0;
    private static final int ONE_ROW = 1;

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        Book createdBook = new Book();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_INDEX_PARAMETER, book.getTitle());
            statement.setBigDecimal(SECOND_INDEX_PARAMETER, book.getPrice());
            int affectedRows = statement.executeUpdate();
            checkInsertedRowsAmount(affectedRows);

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()) {
                Long id = generatedKeys.getLong(FIRST_INDEX_PARAMETER);
                createdBook.setId(id);
                createdBook.setTitle(book.getTitle());
                createdBook.setPrice(book.getPrice());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new book: " + book, e);
        }
        return createdBook;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(FIRST_INDEX_PARAMETER, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book createdBook = createBook(resultSet);
                return Optional.of(createdBook);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id. ID = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(createBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all books.", e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(FIRST_INDEX_PARAMETER, book.getTitle());
            statement.setBigDecimal(SECOND_INDEX_PARAMETER, book.getPrice());
            statement.setLong(THIRD_INDEX_PARAMETER, book.getId());
            int affectedRows = statement.executeUpdate();
            checkInsertedRowsAmount(affectedRows);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update the book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(FIRST_INDEX_PARAMETER, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > ZERO_ROWS;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id. ID = " + id, e);
        }
    }

    private void checkInsertedRowsAmount(int affectedRows) {
        if (affectedRows < ONE_ROW) {
            throw new RuntimeException("Expected to insert at least 1 row, but inserted 0 rows.");
        }
    }

    private Book createBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getObject("price", BigDecimal.class));
        return book;
    }
}
