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
import mate.academy.connection.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PRICE = "price";
    private static final int ID_INDEX = 3;
    private static final int TITLE_INDEX = 1;
    private static final int PRICE_INDEX = 2;

    @Override
    public Book create(Book book) {
        String creationRequest = "INSERT INTO books(title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection(creationRequest);
                PreparedStatement statement = connection.prepareStatement(creationRequest,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Need to insert minimum one row");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot save data " + book.getTitle(), e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String searchRequest = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection(searchRequest);
                PreparedStatement statement = connection.prepareStatement(searchRequest)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = parseBook(resultSet);
                return Optional.of(book);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to find book with id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String searchRequest = "SELECT * FROM books";
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection(searchRequest);
                PreparedStatement statement = connection.prepareStatement(searchRequest)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getObject(ID, Long.class);
                String title = resultSet.getString(TITLE);
                BigDecimal price = resultSet.getBigDecimal(PRICE);
                Book book = new Book();
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                bookList.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t find books", e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        String updateRequest = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection(updateRequest);
                PreparedStatement statement = connection.prepareStatement(updateRequest)) {
            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX, book.getPrice());
            statement.setLong(ID_INDEX, book.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataProcessingException("Failed to update book with id: " + book.getId());
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update book with id: " + book.getId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteRequest = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection(deleteRequest);
                PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete book with id: " + id,e);
        }
    }

    private Book parseBook(ResultSet generatedKeys) throws SQLException {
        if (generatedKeys.next()) {
            Long id = generatedKeys.getObject(1, Long.class);
            String title = generatedKeys.getString(2);
            BigDecimal price = generatedKeys.getBigDecimal(3);
            Book book = new Book();
            book.setId(id);
            book.setTitle(title);
            book.setPrice(price);
            return book;
        } else {
            throw new DataProcessingException("Failed to retrieve generated keys");
        }
    }
}
