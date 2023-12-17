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
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRaws = statement.executeUpdate();
            if (affectedRaws < 1) {
                throw new DataProcessingException("Expected to insert at leas one raw, "
                        + "but insert 0 raw");
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException throwables) {
            throw new DataProcessingException("Can not creat connection", throwables);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapBook(id, resultSet);
            }

        } catch (SQLException throwables) {
            throw new DataProcessingException("Can not creat connection", throwables);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getObject("id", Long.class);
                mapBook(id, resultSet).ifPresent(bookList::add);
            }

        } catch (SQLException throwables) {
            throw new DataProcessingException("Can not creat connection", throwables);
        }

        return bookList;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title=?, price=? WHERE id=?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("No rows were updated. Book with id "
                        + book.getId() + " does not exist.");
            }

        } catch (SQLException throwables) {
            throw new DataProcessingException("Can not create connection or execute update query",
                    throwables);
        }
        return book;

    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException throwables) {
            throw new DataProcessingException("Can not creat connection", throwables);
        }
    }

    private Optional<Book> mapBook(Long id, ResultSet resultSet) throws SQLException {
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);

        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return Optional.of(book);
    }
}
