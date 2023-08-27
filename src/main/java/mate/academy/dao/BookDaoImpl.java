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
                    "There is a problem with connection to DB in create method!!", e);
        }
        return book;
    }

    public Optional<Book> findById(Long id) {
        String sqlFindById = "SELECT id, title, price FROM books WHERE id = ?;";
        Book book = new Book();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlFindById)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet == null) {
                return Optional.empty();
            }
            while (resultSet.next()) {
                book = extractBook(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "There is a problem with connection to DB in findById method!!", e);
        }
        return Optional.of(book);
    }

    public List<Book> findAll() {
        String sqlGetAll = "SELECT * FROM books;";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sqlGetAll)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet == null) {
                return books;
            }
            while (resultSet.next()) {
                books.add(extractBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "There is a problem with connection to DB in findAll method!!", e);
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
                    "There is a problem with connection to DB in update method!!", e);
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
            throw new DataProcessingException("There is a problem"
                    + " with connection to DB in delete method!!", e);
        }
        return executionResult > 0;
    }

    private Book extractBook(ResultSet resultSet) {
        try (resultSet) {
            Book book = new Book();
            book.setId(resultSet.getLong(1));
            book.setTitle(resultSet.getString(2));
            book.setPrice(resultSet.getBigDecimal(3));
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "There is a problem with get data "
                            + "from DB in extractBook method!!", e);
        }
    }
}
