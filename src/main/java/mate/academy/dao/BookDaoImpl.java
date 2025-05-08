package mate.academy.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.DBOperationException;
import mate.academy.connectdb.DBConnector;
import mate.academy.lib.Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mate.academy.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Dao
public class BookDaoImpl implements BookDao {
    
    private static final Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);
    
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1,
                    book.getTitle());
            statement.setBigDecimal(2,
                    book.getPrice());
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DBOperationException("Create book failed, 0 rows affected.");
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getLong(1));
                } else {
                    throw new DBOperationException(
                            "Failed to retrieve generated key for book: " + book);
                }
            }
        } catch (SQLException e) {
            throw new DBOperationException("Can't save a book. Book: " + book,
                    e);
        }
        return book;
    }
    
    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setLong(1,
                    id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getBookFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DBOperationException("Can't find the book. Id: " + id,
                    e);
        }
        return Optional.empty();
    }
    
    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DBOperationException("Can't find all books",
                    e);
        }
        return books;
    }
    
    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1,
                    book.getTitle());
            statement.setBigDecimal(2,
                    book.getPrice());
            statement.setLong(3,
                    book.getId());
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DBOperationException("Update book failed, 0 rows affected.");
            }
        } catch (SQLException e) {
            throw new DBOperationException("Can't update the book: " + book,
                    e);
        }
        return book;
    }
    
    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setLong(1,
                    id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DBOperationException("Can't delete the book. Id: " + id,
                    e);
        }
    }
    
    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }
}
