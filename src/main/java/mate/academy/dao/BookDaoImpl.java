package mate.academy.dao;

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
    @Override
    public Book create(Book book) {
        String createRequest =
                "INSERT INTO books (title, price) VALUES(?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createBookStatement
                        = connection.prepareStatement(
                        createRequest, Statement.RETURN_GENERATED_KEYS)) {
            createBookStatement.setString(1, book.getTitle());
            createBookStatement.setString(2, book.getPrice().toString());
            createBookStatement.executeUpdate();
            ResultSet generatedKeys = createBookStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String getRequest =
                "SELECT * FROM books WHERE id = " + id;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getBookStatement =
                        connection.prepareStatement(getRequest)) {
            ResultSet resultSet = getBookStatement.executeQuery();
            if (resultSet.next()) {
                Book book = getBook(resultSet);
                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get book by id " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> allBooksList = new ArrayList<>();
        String getAllRequest = "SELECT id, title, price FROM books;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getAllRequest)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = getBook(resultSet);
                allBooksList.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get books from database", e);
        }
        return allBooksList;
    }

    @Override
    public Book update(Book book) {
        String updateRequest = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(updateRequest)) {
            updateStatement.setString(1, book.getTitle());
            updateStatement.setBigDecimal(2, book.getPrice());
            updateStatement.setLong(3, book.getId());
            int updated = updateStatement.executeUpdate();
            if (updated > 0) {
                return book;
            } else {
                throw new RuntimeException("Can't update book " + book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update book " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String deleteRequest =
                "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteBookStatement
                        = connection.prepareStatement(deleteRequest)) {
            deleteBookStatement.setLong(1, id);
            return deleteBookStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book by id " + id, e);
        }
    }

    private Book getBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject(1, Long.class));
        book.setTitle(resultSet.getString(2));
        book.setPrice(resultSet.getBigDecimal(3));
        return book;
    }
}
