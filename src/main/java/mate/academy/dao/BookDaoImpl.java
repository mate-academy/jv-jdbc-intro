package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (PreparedStatement preparedStatement =
                     ConnectionUtil.getConnection()
                             .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can't create book " + book, e);
        }

        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement preparedStatement =
                     ConnectionUtil.getConnection().prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject("id", Long.class));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getObject("price", BigDecimal.class));
                return Optional.of(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can't find book by id" + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        List<Book> bookList = new ArrayList<>();

        try (PreparedStatement preparedStatement =
                     ConnectionUtil.getConnection().prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getObject("id", Long.class));
                book.setTitle(resultSet.getString("title"));
                book.setPrice(resultSet.getObject("price", BigDecimal.class));
                bookList.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can't find any book", e);
        }

        return bookList;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";

        try (PreparedStatement preparedStatement =
                     ConnectionUtil.getConnection().prepareStatement(sql)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());

            int i = preparedStatement.executeUpdate();

            if (i > 0) {
                return book;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can't delete book by id " + book, e);
        }

        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (PreparedStatement preparedStatement =
                     ConnectionUtil.getConnection().prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            int i = preparedStatement.executeUpdate();

            if (i > 0) {
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can't delete book by id " + id, e);
        }

        return false;
    }
}
