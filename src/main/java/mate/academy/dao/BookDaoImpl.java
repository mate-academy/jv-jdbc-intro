package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.service.DataProcessingException;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books(title,price) VALUES(?,?)";
        try (PreparedStatement statement
                     = ConnectionUtil.getConnection()
                .prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,book.getTitle());
            statement.setBigDecimal(2,book.getPrice());
            int countRows = statement.executeUpdate();
            if (countRows < 1) {
                throw new RuntimeException("Expected 1 Rows but inserted 0 rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t create new book " + book,e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement statement
                     = ConnectionUtil.getConnection().prepareStatement(query)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getBookFromSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t find any data by id " + id,e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (PreparedStatement statement
                     = ConnectionUtil.getConnection().prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book bookFromSet = getBookFromSet(resultSet);
                bookList.add(bookFromSet);
            }
            return bookList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t find any data",e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?,price = ? WHERE id = ?";
        try (PreparedStatement statement
                     = ConnectionUtil.getConnection().prepareStatement(query)) {
            statement.setString(1,book.getTitle());
            statement.setBigDecimal(2,book.getPrice());
            statement.setLong(3,book.getId());
            int countRows = statement.executeUpdate();
            if (countRows < 1) {
                throw new RuntimeException("Expected 1 Rows but updated 0 rows");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update book " + book,e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement statement
                    = ConnectionUtil.getConnection().prepareStatement(query)) {
            statement.setLong(1,id);
            int countRows = statement.executeUpdate();
            return countRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete book by id " + id,e);
        }
    }

    private Book getBookFromSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        return new Book(id,title,price);
    }
}
