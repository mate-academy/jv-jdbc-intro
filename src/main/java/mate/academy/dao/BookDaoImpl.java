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
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_PRICE = "price";

    @Override
    public Book create(Book book) {
        String request = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int changedRows = statement.executeUpdate();
            if (changedRows < 1) {
                throw new RuntimeException("Expected at least 1 row but found: "
                        + changedRows + " rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't add new book", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String request = "SELECT * FROM books WHERE id = ?";
        Book book = new Book();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString(COLUMN_TITLE);
                BigDecimal price = resultSet.getBigDecimal(COLUMN_PRICE);
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(book);
    }

    @Override
    public List<Book> findAll() {
        String request = "SELECT * FROM books";
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getObject(COLUMN_ID, Long.class);
                String title = resultSet.getString(COLUMN_TITLE);
                BigDecimal price = resultSet.getBigDecimal(COLUMN_PRICE);
                Book book = new Book();
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);
                bookList.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection and find all elements", e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        String request = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("Expected at least 1 row but found: "
                        + affectedRows + " rows");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String request = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            preparedStatement.setLong(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
