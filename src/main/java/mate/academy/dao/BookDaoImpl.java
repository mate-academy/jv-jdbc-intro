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
import mate.academy.util.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    Throwable e;
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (price, title) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setBigDecimal(1, book.getPrice());
            preparedStatement.setString(2, book.getTitle());

            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows < 1) {
                throw new DataProcessingException("Expected insert atleast 1 row, but inserted 0 rows!", e);
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a new book! " + book, e);
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
                String model = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                Book book = bookSetter(model, price, id);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id! " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> list = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String model = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                Long id = resultSet.getObject("id", Long.class);
                Book book = bookSetter(model, price, id);
                list.add(book);
            }
            return list;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find information about all books!", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET price = ?, title = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBigDecimal(1, book.getPrice());
            statement.setString(2, book.getTitle());
            statement.setObject(3, book.getId());
            int affectiveRows = statement.executeUpdate();
            if (affectiveRows == 0) {
                throw new DataProcessingException("Expected to insert atleast 1 row, "
                       + "but inserted 0 rows!", e);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book with id: " + book.getId(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int affectiveRows = preparedStatement.executeUpdate();
            if (affectiveRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id: " + id, e);
        }
        return false;
    }

    private Book bookSetter(String model, BigDecimal price, Long id) {
        Book book = new Book();
        book.setTitle(model);
        book.setPrice(price);
        book.setId(id);
        return book;
    }
}
