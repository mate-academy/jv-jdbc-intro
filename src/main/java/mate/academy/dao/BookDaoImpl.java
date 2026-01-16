package mate.academy.dao;

import mate.academy.Connection.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book (price, title) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setBigDecimal(1, book.getPrice());
            preparedStatement.setString(2, book.getTitle());

            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows < 1) {
                throw new RuntimeException("Expected insert atleast 1 row, but inserted 0 rows!");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create a new book!", e);
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
                 String model = resultSet.getString("title");
                 BigDecimal price = resultSet.getObject("price", BigDecimal.class);

                 Book book = new Book();
                 book.setTitle(model);
                 book.setPrice(price);
                 book.setId(id);
                 return Optional.of(book);
             }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find book by id! ", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        List <Book> list = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                    String model = resultSet.getString("title");
                    BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                    Long id = resultSet.getObject("id", Long.class);
                    Book book = new Book();
                    book.setTitle(model);
                    book.setPrice(price);
                    book.setId(id);
                    list.add(book);
                }
            return list;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find information about all books!", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE book SET price = ?, title = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
             statement.setBigDecimal(1, book.getPrice());
            statement.setString(2, book.getTitle());
             statement.setObject(3, book.getId());
            int affectiveRows = statement.executeUpdate();
            if (affectiveRows == 0) {
                throw new RuntimeException("Expected to insert atleast 1 row, but inserted 0 rows!");
            }
        } catch (SQLException e) {
                 throw new DataProcessingException("Can't update book with id: " + book.getId(), e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
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
}
