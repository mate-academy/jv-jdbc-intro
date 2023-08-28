package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.repository.BookDao;
import mate.academy.utility.ConnectionUtil;

@Dao
public class BookDaoImpl implements BookDao {

    public Book create(Book book) {
        String sqlInsert = "INSERT INTO books (title, price) VALUES (?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can not insert new value", e);
        }
        return book;
    }

    public Optional<Book> findById(Long id) {
        String sqlFindById = "SELECT id, title, price FROM books WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlFindById)) {
            preparedStatement.setLong(1, id);
            if (preparedStatement.execute()) {
                ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    return Optional.of(extractBook(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can not find book by id: " + id, e);
        }
        return Optional.empty();
    }

    public List<Book> findAll() {
        String sqlGetAll = "SELECT * FROM books;";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlGetAll)) {
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    books.add(extractBook(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can not get all values from DB", e);
        }
        return books;
    }

    public Book update(Book book) {
        String sqlUpdate = "UPDATE books SET title = ?, price = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            preparedStatement.setLong(3, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't update value in the DB", e);
        }
        return book;
    }

    public boolean deleteById(Long id) {
        String sqlDelete = "DELETE FROM books WHERE id = ?;";
        int executionResult;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {
            preparedStatement.setLong(1, id);
            executionResult = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete value by id: " + id, e);
        }
        return executionResult > 0;
    }

    private Book extractBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong(1));
        book.setTitle(resultSet.getString(2));
        book.setPrice(resultSet.getBigDecimal(3));
        return book;
    }
}
