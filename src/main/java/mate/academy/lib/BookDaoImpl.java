package mate.academy.lib;

import mate.academy.exception.DataProcessingException;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int ID_INDEX = 1;
    private static final int TITLE_INDEX = 1;
    private static final int PRICE_INDEX = 2;
    private static final int UPDATE_INDEX = 3;

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX, book.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(ID_INDEX, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(ID_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            Book book = null;
            if (resultSet.next()) {
                book = parseBook(resultSet);
            }
            return Optional.of(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book with id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
         ResultSet resultSet = statement.executeQuery(sql);
         while (resultSet.next()) {
             books.add(parseBook(resultSet));
         }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(UPDATE_INDEX, book.getId());
            statement.setString(TITLE_INDEX, book.getTitle());
            statement.setBigDecimal(PRICE_INDEX, book.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book in DB " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(ID_INDEX, id);
            statement.executeUpdate();
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete data from DB"+ id, e);
        }
    }

    private Book parseBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id",Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getObject("price", BigDecimal.class));
        return book;
    }
}
