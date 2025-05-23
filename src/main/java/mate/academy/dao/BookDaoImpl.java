package mate.academy.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES(?, ?);";
        try (PreparedStatement statement = ConnectionUtil.getConnection().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int effectedRows = statement.executeUpdate();
            if (effectedRows < 1) {
                throw new DataProcessingException("Expected to update at least one row, "
                        + "but updated 0 rows", new Throwable());
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                BigInteger id = generatedKeys.getObject(1, BigInteger.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not create new book: " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(BigInteger id) {
        String sql = "SELECT * FROM books WHERE id = ?;";
        try (PreparedStatement preparedStatement = ConnectionUtil.getConnection()
                .prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = parseRow(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find book with id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books;";
        try (PreparedStatement statement = ConnectionUtil.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> booksList = new ArrayList<>();
            while (resultSet.next()) {
                Book book = parseRow(resultSet);
                booksList.add(book);
            }
            return booksList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not find books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = ConnectionUtil.getConnection()
                .prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setObject(3, book.getId());
            int effectedRow = preparedStatement.executeUpdate();
            if (effectedRow < 1) {
                throw new DataProcessingException("Can not update book: " + book, new Throwable());
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(BigInteger id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement preparedStatement = ConnectionUtil.getConnection()
                .prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            int effectedRow = preparedStatement.executeUpdate();
            return effectedRow > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete book with id: " + id, e);
        }
    }

    private Book parseRow(ResultSet resultSet) throws SQLException {
        BigInteger id = resultSet.getObject("id", BigInteger.class);
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getObject("price", BigDecimal.class);
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setPrice(price);
        return book;
    }
}
