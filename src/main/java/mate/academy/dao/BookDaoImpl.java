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
import mate.academy.lib.Dao;
import mate.academy.lib.DataProcessingException;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books(TITLE,PRICE) VALUES(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,book.getTitle());
            statement.setBigDecimal(2,book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row,"
                       + " but inserted 0 rows!");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long bookId = generatedKeys.getObject(1, Long.class);
                book.setId(bookId);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not add book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE ID = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get element by book id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(parseResultSet(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get all books " + books, e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET TITLE = ?, PRICE = ? WHERE ID = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1,book.getTitle());
            statement.setBigDecimal(2,book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at least one row,"
                        + " but inserted 0 rows!");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                String bookTitle = generatedKeys.getString("TITLE");
                BigDecimal bookPrice = generatedKeys.getObject("PRICE",BigDecimal.class);
                book.setTitle(bookTitle);
                book.setPrice(bookPrice);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update book" + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE ID = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete a book by id " + id, e);
        }
    }

    private Book parseResultSet(ResultSet resultSet) {
        try {
            Long bookId = resultSet.getObject("ID", Long.class);
            String bookTitle = resultSet.getString("TITLE");
            BigDecimal bookPrice = resultSet.getObject("PRICE", BigDecimal.class);
            Book book = new Book();
            book.setId(bookId);
            book.setTitle(bookTitle);
            book.setPrice(bookPrice);
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not parse resultset " + resultSet, e);
        }
    }
}
