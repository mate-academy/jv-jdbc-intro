package mate.academy.lib;

import mate.academy.lib.BookDao;
import mate.academy.lib.Dao;
import mate.academy.lib.Book;
import mate.academy.lib.DataProcessingException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    private static final String URL = "jdbc:mysql://localhost:3306/library?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Uen9opX";

    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getLong(1));
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create book " + book, e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = parseBook(resultSet);
                return Optional.of(book);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't find book by id " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(parseBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't retrieve all books", e);
        }
    }

    @Override
    public Book update(Book book) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());
            statement.executeUpdate();
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't update book " + book, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete book by id " + id, e);
        }
    }

    private Book parseBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        BigDecimal price = resultSet.getBigDecimal("price");
        Book book = new Book(title, price);
        book.setId(id);
        return book;
    }
}