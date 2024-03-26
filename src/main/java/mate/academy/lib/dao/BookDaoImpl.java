package mate.academy.lib.dao;

import connection.ConnectionUtil;
import exception.DataProcessingException;
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
import model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2,book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected insert one row, but was zero rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create a new book " + book, e);
        }
        return book;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE book SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2,book.getPrice());
            statement.setLong(3,book.getId());
            int executeUpdate = statement.executeUpdate();
            if (executeUpdate < 1) {
                throw new DataProcessingException("It must be one row, but was zero rows");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update book with id = " + book.getId());
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(parseQuery(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t connect to DB", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM book";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bookList.add(parseQuery(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookList;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t read from DB", e);
        }
    }

    private Book parseQuery(ResultSet resultSet) {
        Book book;
        try {
            book = new Book();
            Long id = resultSet.getObject(1, Long.class);
            String title = resultSet.getNString(2);
            BigDecimal price = resultSet.getBigDecimal(3);
            book.setId(id);
            book.setTitle(title);
            book.setPrice(price);
        } catch (DataProcessingException e) {
            throw new DataProcessingException("Can`t read from DB", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;
    }
}
