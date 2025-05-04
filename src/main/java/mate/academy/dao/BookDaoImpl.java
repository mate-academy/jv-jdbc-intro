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

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES(?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot add a book" + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Book foundBook = null;
            if (resultSet.next()) {
                foundBook = parseBook(resultSet);
            }
            return Optional.ofNullable(foundBook);
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot find a book with id:" + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        List<Book> booksList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book recievedBook = parseBook(resultSet);
                booksList.add(recievedBook);
            }
            return booksList;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot get all data from DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setObject(2, book.getPrice());
            preparedStatement.setObject(3, book.getId());
            preparedStatement.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update such book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot delete book with id" + id, e);

        }
    }

    private static Book parseBook(ResultSet resultSet) {
        Book recievedBook = new Book();
        try {
            recievedBook.setId(resultSet.getObject("id", Long.class));
            recievedBook.setTitle(resultSet.getString("title"));
            recievedBook.setPrice(resultSet.getObject("price", BigDecimal.class));
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot process result set", e);
        }
        return recievedBook;
    }
}
