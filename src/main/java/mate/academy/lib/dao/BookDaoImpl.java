package mate.academy.lib.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.ConnectionUtil;
import mate.academy.lib.exception.DataProcessingException;
import mate.academy.lib.injector.Dao;
import mate.academy.lib.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books(title, price) VALUES(?, ?)";
        
        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement statement = 
                            connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();
            
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getObject("id", Long.class));
                return book;
            }
            
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create book with id: " + book.getId(), e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(getBookFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("SQL error occurred", e);
        }
    }
        
    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("SQL error occurred", e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection(); 
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DataProcessingException("No rows were affected during update", null);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book with id: " + book.getId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id, e);
        }
    }
    
    private Book getBookFromResultSet(ResultSet resultSet) {
        Book book = new Book();
        try {
            book.setId(resultSet.getObject("id", Long.class));
            book.setTitle(resultSet.getString("title"));
            book.setPrice(resultSet.getObject("price", BigDecimal.class));
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't extract book from ResultSet", e);
        }
    }
}
