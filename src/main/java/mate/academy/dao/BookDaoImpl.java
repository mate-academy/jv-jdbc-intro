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
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {
    private static final int FIRST_STATEMENT_PAREMETER_INDEX = 1;
    private static final int SECOND_STATEMENT_PAREMETER_INDEX = 2;
    private static final int THIRD_STATEMENT_PAREMETER_INDEX = 3;
    private static final int ID_COLUMN_INDEX = 1;

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books(title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_STATEMENT_PAREMETER_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_STATEMENT_PAREMETER_INDEX, book.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getObject(ID_COLUMN_INDEX, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant save book with title = " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(FIRST_STATEMENT_PAREMETER_INDEX, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant find book by id " + id, e);
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
                books.add(createBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cant find all books", e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(FIRST_STATEMENT_PAREMETER_INDEX, book.getTitle());
            statement.setBigDecimal(SECOND_STATEMENT_PAREMETER_INDEX, book.getPrice());
            statement.setLong(THIRD_STATEMENT_PAREMETER_INDEX, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Cant update book: " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(FIRST_STATEMENT_PAREMETER_INDEX, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Cant delete book by id " + id, e);
        }
    }

    private Book createBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        return new Book(id, title, price);
    }
}
