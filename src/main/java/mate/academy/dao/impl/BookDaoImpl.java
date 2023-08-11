package mate.academy.dao.impl;

import mate.academy.connection.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.dao.BookDao;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_INDEX_PARAMETER, book.getTitle());
            statement.setBigDecimal(SECOND_INDEX_PARAMETER, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < ONE_ROW) {
                throw new DataProcessingException("Expected to insert at least one row, but inserted 0 rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(FIRST_INDEX_PARAMETER, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create new book = " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(FIRST_INDEX_PARAMETER, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(parseDataToBook(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not to find the book by id = " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        List<Book> booksList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = parseDataToBook(resultSet);
                booksList.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not to find any book in DB", e);
        }
        return booksList;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(FIRST_INDEX_PARAMETER, book.getTitle());
            statement.setObject(SECOND_INDEX_PARAMETER, book.getPrice());
            statement.setLong(THIRD_INDEX_PARAMETER, book.getId());
            int affectedRow = statement.executeUpdate();
            if (affectedRow < ZERO_ROWS) {
                throw new DataProcessingException("Expected updated book"
                        + book + " But it's not happened");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update the book " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(FIRST_INDEX_PARAMETER, id);
            return statement.executeUpdate() > ZERO_ROWS;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not to delete the book by id = " + id, e);
        }
    }

    private Book parseDataToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getObject("price", BigDecimal.class));
        return book;
    }
}
