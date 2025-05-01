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
import mate.academy.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int FIRST_PARAMETER = 1;
    private static final int SECOND_PARAMETER = 2;
    private static final int THIRD_PARAMETER = 3;
    private static final int MIN_AFFECTED_ROWS = 1;
    private static final int ID_INDEX = 1;
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String PRICE = "price";

    @Override
    public Book create(Book book) {
        String sqlQuery = "INSERT INTO  books (title, price) values(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement statement
                        = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER, book.getTitle());
            statement.setBigDecimal(SECOND_PARAMETER, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < MIN_AFFECTED_ROWS) {
                throw new DataProcessingException("""
                        Expected to insert at least one row,
                        but inserted 0 rows""", new Exception());
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not create book" + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sqlQuery = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(FIRST_PARAMETER, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getObject(TITLE, String.class);
                BigDecimal price = resultSet.getObject(PRICE, BigDecimal.class);
                Book book = setBook(id, title, price);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find the book by id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sqlQuery = "SELECT * FROM books";
        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> bookList = new ArrayList<>();
            while (resultSet.next()) {
                Book book = new Book();
                Long id = resultSet.getObject(ID, Long.class);
                String title = resultSet.getObject(TITLE, String.class);
                BigDecimal price = resultSet.getObject(PRICE, BigDecimal.class);
                book = setBook(id, title, price);
                bookList.add(book);
            }
            return bookList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sqlQuery = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(FIRST_PARAMETER, book.getTitle());
            statement.setBigDecimal(SECOND_PARAMETER, book.getPrice());
            statement.setLong(THIRD_PARAMETER, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < MIN_AFFECTED_ROWS) {
                throw new DataProcessingException("""
                        Expected to update at least one row,
                        but updated 0 rows""", new Exception());
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update the book", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sqlQuery = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(FIRST_PARAMETER, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows >= MIN_AFFECTED_ROWS;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete the book", e);
        }
    }

    private Book setBook(Long id, String title, BigDecimal price) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
