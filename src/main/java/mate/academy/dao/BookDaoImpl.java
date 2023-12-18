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
import mate.academy.exception.DataProcessException;
import mate.academy.lib.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book (title,price) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessException("Can not inserted a row");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessException("Can't create connection ", e);
        }
        return null;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long idResult = resultSet.getObject("id", Long.class);
                String titleResult = resultSet.getString("title");
                BigDecimal priceResult = resultSet.getObject("price", BigDecimal.class);
                Book book = new Book();
                book.setId(idResult);
                book.setTitle(titleResult);
                book.setPrice(priceResult);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessException("Can't get a book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        List<Book> listResult = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Long idResult = resultSet.getObject("id", Long.class);
                String titleResult = resultSet.getString("title");
                BigDecimal priceResult = resultSet.getObject("price", BigDecimal.class);
                Book book = new Book();
                book.setId(idResult);
                book.setTitle(titleResult);
                book.setPrice(priceResult);
                listResult.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessException("Can't create connection ", e);
        }
        return listResult;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE book SET title = ?,price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessException("Can not update a book " + book);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
                return book;
            }
        } catch (SQLException e) {
            throw new DataProcessException("Can't create connection " + book, e);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessException("Can not delete a book for id = " + id);
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessException("Can't create connection " + id, e);
        }
    }
}
