package mate.academy.dao;

import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setBigDecimal(2, book.getPrice());
            if (preparedStatement.executeUpdate() < 1) {
                throw new RuntimeException("Method should add at least 1 book to DB");
            }
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add a book: " + book + " to DB", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Book foundBook = null;
            if (resultSet.next()) {
                foundBook = processResultSet(resultSet);
            }
            return Optional.ofNullable(foundBook);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find a book with id: " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        List<Book> allBooks = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book recievedBook = processResultSet(resultSet);
                allBooks.add(recievedBook);
            }
            return allBooks;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all data from DB", e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setObject(2, book.getPrice());
            statement.setObject(3, book.getId());
            if (statement.executeUpdate() < 1) {
                throw new RuntimeException("Method should update at least 1 book in DB");
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update such book: " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete book with id: " + id, e);
        }
    }

    private static Book processResultSet(ResultSet resultSet) {
        Book recievedBook = new Book();
        try {
            recievedBook.setId(resultSet.getObject("id", Long.class));
            recievedBook.setTitle(resultSet.getString("title"));
            recievedBook.setPrice(resultSet.getObject("price", BigDecimal.class));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't process ResultSet", e);
        }
        return recievedBook;
    }
}
