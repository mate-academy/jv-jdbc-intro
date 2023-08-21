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
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title,price) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int processedRows = statement.executeUpdate();
            if (processedRows < 1) {
                throw new RuntimeException("Unsuccessful operation. Inserted 0 rows");
            }
            ResultSet generatedKey = statement.getGeneratedKeys();
            if (generatedKey.next()) {
                Long id = generatedKey.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't save a book " + book, e.getCause());
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = getBook(id, resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to connect to the DB", e.getCause());
        }
        throw new RuntimeException("Can't get a book by id " + id + ". There's no existing book");
    }

    @Override
    public List<Book> findAll() {
        List<Book> booksFromDB = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                Book book = getBook(id, resultSet);
                booksFromDB.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't get a List of books from DB", e.getCause());
        }
        return booksFromDB;
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new RuntimeException("Unsuccessful operation. Updated 0 rows");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update the book " + book, e.getCause());
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        int updatetedRows = 0;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            updatetedRows = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete the book by id " + id, e.getCause());
        }
        return updatetedRows > 0;
    }

    private static Book getBook(Long id, ResultSet resultSet) throws SQLException {
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        return new Book(id, title, price);
    }
}

